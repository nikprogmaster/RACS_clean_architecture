package com.example.racs.Authorization;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthorizationApi {

    @POST("token/auth/")
    Call<AuthPOJO> authentication(@Body AuthPostPOJO body);

}
