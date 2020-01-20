package com.example.racs.Locks;

import retrofit2.http.*;
import retrofit2.Call;

//интерфейс для получения информации от сервера
public interface LockApi {

    //запрос на получение списка замков
    @GET("locks")
    Call<LocksPOJO> getLocks(@Header("Authorization") String token, @Query("count") int count);

    //запрос на получение даты запуска сервере
    @GET()
    Call<String> getDate(@Header("Authorization") String token, @Url String url);

    //добавление замков
    @POST("locks/")
    Call<LocksPOJO> addLock(@Header("Authorization") String token, @Body LockPostPOJO body);

    @DELETE("locks/{id}/")
    Call<Void> deleteLock(@Header("Authorization") String token, @Path("id") int id);
}
