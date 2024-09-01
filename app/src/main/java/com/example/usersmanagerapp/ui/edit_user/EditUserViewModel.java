package com.example.usersmanagerapp.ui.edit_user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.usersmanagerapp.models.User;
import com.example.usersmanagerapp.repositories.UserRepository;

import java.util.List;

public class EditUserViewModel extends AndroidViewModel {
    private final MutableLiveData<List<User>> usersLiveData;
    private final UserRepository userRepository;

    public EditUserViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository(application.getApplicationContext());
        this.usersLiveData = new MutableLiveData<>();
    }
}