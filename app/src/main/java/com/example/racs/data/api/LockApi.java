package com.example.racs.data.api;

import com.example.racs.model.data.LocksEntityData;

import io.reactivex.Completable;
import io.reactivex.Single;
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
    Single<LocksEntityData> getLocks(@Header("Authorization") String token, @Query("count") int count);

    //запрос на получение даты запуска сервере
    @Deprecated
    @GET()
    Call<String> getDate(@Header("Authorization") String token, @Url String url);


    @DELETE("locksList/{id}/")
    Completable deleteLock(@Header("Authorization") String token, @Path("id") int id);
}
