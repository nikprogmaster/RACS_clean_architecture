package com.example.racs.Access;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccessApi {

    @GET("accesses")
    Call<AccessPOJO> getAccesses(@Header("Authorization") String token, @Query("count") int count);

    @POST("accesses/")
    Call<AccessPOJO> postAccess(@Header("Authorization") String token, @Body AccessPostPOJO body);

    @DELETE("accesses/{id}/")
    Call<Void> deleteAccess(@Header("Authorization") String token, @Path("id") int id);
}
