package com.example.usersmanagerapp.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.usersmanagerapp.database.UsersDatabase;
import com.example.usersmanagerapp.managers.FirstLoadManager;
import com.example.usersmanagerapp.models.User;
import com.example.usersmanagerapp.response.UsersResponse;
import com.example.usersmanagerapp.retrofit.ApiRequest;
import com.example.usersmanagerapp.retrofit.RetrofitRequest;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private static final int MAX_RETRIES = 3;

    private final ApiRequest apiRequest;
    private final UsersDatabase usersDatabase;
    private final FirstLoadManager firstLoadManager;
    private final ExecutorService executorService;

    public UserRepository(Context context) {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        usersDatabase = UsersDatabase.getDatabase(context);
        firstLoadManager = new FirstLoadManager(context);
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<User>> getUsers() {
        MutableLiveData<List<User>> data = new MutableLiveData<>();
        executorService.execute(() -> {
            if (!firstLoadManager.isFirstLoad()) {
                data.postValue(usersDatabase.usersDao().getUsers());
            } else {
                fetchUsersWithPagination(1, data, 0);
            }
        });
        return data;
    }

    public void insertUser(User user) {
        executorService.execute(() -> usersDatabase.usersDao().insertUser(user));
    }

    public void deleteUser(User user) {
        executorService.execute(() -> usersDatabase.usersDao().deleteUser(user));
    }

    private void fetchUsersWithPagination(int pageNum, MutableLiveData<List<User>> data, int retryCount) {
        apiRequest.getUsers(pageNum)
                .enqueue(new Callback<UsersResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<UsersResponse> call, @NonNull Response<UsersResponse> response) {
                        if (response.body() != null && response.body().getUsers() != null) {
                            List<User> users = response.body().getUsers();

                            executorService.execute(() -> {
                                usersDatabase.usersDao().insertUsers(users);

                                executorService.execute(() -> {
                                    List<User> allUsers = usersDatabase.usersDao().getUsers();
                                    data.postValue(allUsers);
                                });
                            });

                            if (pageNum < response.body().getTotalPages()) {
                                fetchUsersWithPagination(pageNum + 1, data, 0);
                            } else {
                                firstLoadManager.setFirstLoadComplete();
                            }
                        } else {
                            Log.e(TAG, "API response is empty or null");
                            data.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UsersResponse> call, @NonNull Throwable t) {
                        Log.e(TAG, "Failed to fetch users from API, retryCount: " + retryCount, t);

                        if (retryCount < MAX_RETRIES) {
                            fetchUsersWithPagination(pageNum, data, retryCount + 1);
                        } else {
                            Log.e(TAG, "Max retries reached. Giving up on fetching page: " + pageNum);
                            data.setValue(null);
                        }
                    }
                });
    }
}