package com.example.usersmanagerapp.models;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "USERS")
public class User {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    private String email;

    @SerializedName("first_name")
    @Expose
    @ColumnInfo(name = "first_name")
    private String firstName;

    @SerializedName("last_name")
    @Expose
    @ColumnInfo(name = "last_name")
    private String lastName;

    @SerializedName("avatar")
    @Expose
    @ColumnInfo(name = "avatar")
    private String imageUrl;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        Log.d("User", "toString: " + id + " " + email + " " + firstName + " " + lastName + " " + imageUrl);
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", avatar='" + imageUrl + '\'' +
                '}';
    }

}
