package com.example.racs.Users;

import retrofit2.http.*;
import retrofit2.Call;
import retrofit2.Response;

public interface UserApi {

    @GET("users")
    Call<UsersPOJO> getUsers(@Header("Authorization") String token, @Query("count") int count);

    @POST("users/")
    Call<UsersPOJO> addUser(@Header("Authorization") String token, @Body UserPostPOJO body);

    @DELETE("users/{id}/")
    Call<Response<Void>> deleteUser(@Header("Authorization") String token, @Path("id") int id);
}
