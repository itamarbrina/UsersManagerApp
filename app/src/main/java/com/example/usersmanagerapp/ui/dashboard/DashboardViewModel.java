package com.example.usersmanagerapp.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.usersmanagerapp.repositories.UserRepository;
import com.example.usersmanagerapp.response.UsersResponse;

public class DashboardViewModel extends AndroidViewModel {

    private MutableLiveData<Integer> progressBarLiveData;
    private UserRepository userRepository;
    private LiveData<UsersResponse> usersResponseLiveData;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository();
        this.usersResponseLiveData = userRepository.getUsers();
        this.progressBarLiveData = new MutableLiveData<>();
    }

    public LiveData<UsersResponse> getUsersResponseLiveData() {
        return usersResponseLiveData;
    }

    public LiveData<Integer> getProgressBarLiveData() {
        return progressBarLiveData;
    }

    public void setProgressBarLiveData(Integer visibility) {
        progressBarLiveData.setValue(visibility);
    }

}