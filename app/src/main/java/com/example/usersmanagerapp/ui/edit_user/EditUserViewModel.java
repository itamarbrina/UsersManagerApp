package com.example.usersmanagerapp.ui.edit_user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditUserViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EditUserViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}