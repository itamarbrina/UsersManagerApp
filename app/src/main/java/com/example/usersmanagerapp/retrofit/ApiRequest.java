package com.example.usersmanagerapp.retrofit;

import com.example.usersmanagerapp.response.UsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {
    @GET("api/users")
    Call<UsersResponse> getUsers(
            @Query("page")
            int numPage
    );

    default Call<UsersResponse> getUsers() {
        return getUsers(1);
    }
}
