package com.example.usersmanagerapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.usersmanagerapp.models.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UsersDatabase extends RoomDatabase {
    private static volatile UsersDatabase INSTANCE;

    public abstract UsersDao usersDao();

    public static UsersDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UsersDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    UsersDatabase.class, "user_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
