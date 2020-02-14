package com.example.racs.data.api;

import android.util.Log;

import com.example.racs.data.entities.AuthEntity;
import com.example.racs.data.entities.AuthPostEntity;
import com.example.racs.data.entities.RefreshEntity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class AuthorizationImpl {

    private AuthorizationApi authApi = App.getRetrofit().create(AuthorizationApi.class);
    private AuthEntity authEntity;

    public AuthEntity authentication(AuthPostEntity body) {
        Call<AuthEntity> call;
        try {
            if(authEntity == null){
                Log.i("Impl", "authEntity == null");
                call = authApi.authentication(body);
                Response<AuthEntity> response = call.execute();
                authEntity = response.body();
            } else {
                Log.i("Impl", "authEntity != null");
                call = authApi.refreshAuth(new RefreshEntity(authEntity.getRefresh()));
                Response<AuthEntity> response = call.execute();
                authEntity.setAccess(response.body().getAccess());
            }
            return authEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authEntity;
    }

}
