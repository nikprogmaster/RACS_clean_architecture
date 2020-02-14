package com.example.racs.data.api;

import com.example.racs.data.entities.LockPostEntity;
import com.example.racs.data.entities.LocksEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

//интерфейс для получения информации от сервера
public interface LockApi {

    //запрос на получение списка замков
    @GET("locks/")
    Call<LocksEntity> getLocks(@Header("Authorization") String token, @Query("count") int count);

    //запрос на получение даты запуска сервере
    @GET()
    Call<String> getDate(@Header("Authorization") String token, @Url String url);

    //добавление замков
    @POST("locks/")
    Call<LocksEntity> addLock(@Header("Authorization") String token, @Body LockPostEntity body);

    @DELETE("locksList/{id}/")
    Call<Void> deleteLock(@Header("Authorization") String token, @Path("id") int id);
}
