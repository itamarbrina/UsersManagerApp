package com.example.usersmanagerapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.usersmanagerapp.response.UsersResponse;
import com.example.usersmanagerapp.retrofit.ApiRequest;
import com.example.usersmanagerapp.retrofit.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    //    private static final String TAG = UserRepository.class.getSimpleName();
    private final ApiRequest apiRequest;

    public UserRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public LiveData<UsersResponse> getUsers() {
        final MutableLiveData<UsersResponse> data = new MutableLiveData<>();
        apiRequest.getUsers(1)
                .enqueue(new Callback<UsersResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<UsersResponse> call, @NonNull Response<UsersResponse> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                            Log.d("TAG", "onResponse: " + response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UsersResponse> call, @NonNull Throwable t) {
                        data.setValue(null);
                        // todo take care of failures
                    }
                });
        return data;
    }
}
