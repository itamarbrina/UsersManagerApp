package com.example.usersmanagerapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.usersmanagerapp.models.User;

import java.util.ArrayList;

@Dao
public interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertUser(User user);

    @Query("SELECT * FROM users")
    ArrayList<User> getUsers();

    @Delete
    void deleteUser(User user);
}
