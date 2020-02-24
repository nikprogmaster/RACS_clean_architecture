package com.example.racs.data.api;

import com.example.racs.model.data.UserPostEntityData;
import com.example.racs.model.data.UsersEntityData;

import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    @GET("users/")
    Observable<UsersEntityData> getUsers(@Header("Authorization") String token, @Query("page") int page, @Query("count") int count);

    @POST("users/")
    Completable addUser(@Header("Authorization") String token, @Body UserPostEntityData body);

    @DELETE("users/{id}/")
    Completable deleteUser(@Header("Authorization") String token, @Path("id") int id);
}
