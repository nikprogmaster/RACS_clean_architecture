package com.example.racs.data.api;

import com.example.racs.model.data.AccessEntityData;
import com.example.racs.model.data.AccessPostEntityData;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccessApi {

    @GET("accesses/")
    Single<AccessEntityData> getAccesses(@Header("Authorization") String token, @Query("count") int count);

    @POST("accesses/")
    Completable postAccess(@Header("Authorization") String token, @Body AccessPostEntityData body);

    @DELETE("accesses/{id}/")
    Completable deleteAccess(@Header("Authorization") String token, @Path("id") int id);
}
