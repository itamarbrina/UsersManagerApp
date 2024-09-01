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
import java.util.Map;

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

    public boolean isUserValid(User user, Map<String, String> errorMessages) {
        boolean isValid = true;

        if (user.getFirstName().isEmpty()) {
            errorMessages.put("firstNameError", "First name is required");
            isValid = false;
        }

        if (user.getLastName().isEmpty()) {
            errorMessages.put("lastNameError", "Last name is required");
            isValid = false;
        }

        if (user.getEmail().isEmpty()) {
            errorMessages.put("emailError", "Email is required");
            isValid = false;
        }
        if (user.getImageUrl().isEmpty()) {
            user.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcyI9Cvp53aaP9XeRn-ZKbJDH2QaWC72O26A&s");
        }

        return isValid;
    }
}

