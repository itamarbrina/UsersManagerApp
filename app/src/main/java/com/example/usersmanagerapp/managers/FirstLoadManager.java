package com.example.usersmanagerapp.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class FirstLoadManager {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_FIRST_LOAD = "first_load";

    private SharedPreferences sharedPreferences;

    public FirstLoadManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isFirstLoad() {
        return sharedPreferences.getBoolean(KEY_FIRST_LOAD, true);
    }

    public void setFirstLoadComplete() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_FIRST_LOAD, false);
        editor.apply();
    }
}
