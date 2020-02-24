package com.example.racs.data.api;

import com.example.racs.model.data.AuthEntityData;
import com.example.racs.model.data.AuthPostEntityData;
import com.example.racs.model.data.RefreshEntityData;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthorizationApi {

    @POST("token/auth/")
    Single<AuthEntityData> authentication(@Body AuthPostEntityData body);

    @POST("token/refresh/")
    Observable<AuthEntityData> refreshAuth(@Body RefreshEntityData body);


}
