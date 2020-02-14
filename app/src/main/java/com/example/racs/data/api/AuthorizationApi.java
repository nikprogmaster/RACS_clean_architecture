package com.example.racs.data.api;

import com.example.racs.data.entities.AuthEntity;
import com.example.racs.data.entities.AuthPostEntity;
import com.example.racs.data.entities.RefreshEntity;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthorizationApi {

    @POST("token/auth/")
    Call<AuthEntity> authentication(@Body AuthPostEntity body);

    @POST("token/refresh/")
    Call<AuthEntity> refreshAuth(@Body RefreshEntity body);


}
