package com.example.usersmanagerapp.ui.dashboard;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.usersmanagerapp.models.User;
import com.example.usersmanagerapp.repositories.UserRepository;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private final MutableLiveData<Integer> progressBarLiveData;
    private final MutableLiveData<List<User>> usersLiveData;
    private final UserRepository userRepository;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository(application.getApplicationContext());
        this.usersLiveData = new MutableLiveData<>();
        this.progressBarLiveData = new MutableLiveData<>();
    }

    public LiveData<List<User>> getUsersLiveData() {
        setProgressBarLiveData(View.VISIBLE);
        userRepository.getUsers().observeForever(users -> {
            usersLiveData.setValue(users);
            setProgressBarLiveData(View.GONE);
        });

        return usersLiveData;
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
    }

    public void deleteUser(User user) {
        userRepository.deleteUser(user);
    }

    public LiveData<Integer> getProgressBarLiveData() {
        return progressBarLiveData;
    }

    public void setProgressBarLiveData(Integer visibility) {
        progressBarLiveData.setValue(visibility);
    }

}
