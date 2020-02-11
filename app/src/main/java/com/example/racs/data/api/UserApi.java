package com.example.racs.data.api;

import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.entities.UsersEntity;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    @GET("users")
    Call<UsersEntity> getUsers(@Header("Authorization") String token, @Query("count") int count);

    @POST("users/")
    Call<UsersEntity> addUser(@Header("Authorization") String token, @Body UserPostEntity body);

    @DELETE("users/{id}/")
    Call<Response<Void>> deleteUser(@Header("Authorization") String token, @Path("id") int id);
}
