package com.example.usersmanagerapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.usersmanagerapp.models.User;

import java.util.List;

@Dao
public interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUsers(List<User> users);

    @Query("SELECT * FROM USERS")
    List<User> getUsers();

    @Delete
    void deleteUser(User user);
}
