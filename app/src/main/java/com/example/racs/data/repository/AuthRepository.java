package com.example.racs.data.repository;

import com.example.racs.data.api.AuthorizationApi;
import com.example.racs.model.data.AuthEntityData;
import com.example.racs.model.data.AuthPostEntityData;
import com.example.racs.model.data.RefreshEntityData;

import io.reactivex.Observable;
import io.reactivex.Single;

public class AuthRepository implements IAuthRepository {

    private final AuthorizationApi authApi;

    public AuthRepository(AuthorizationApi authApi) {
        this.authApi = authApi;
    }

    @Override
    public Single<AuthEntityData> getTokens(AuthPostEntityData body) {
        return authApi.authentication(body);
    }

    @Override
    public Observable<AuthEntityData> refreshTokens(RefreshEntityData body) {
        return authApi.refreshAuth(body);
    }

}
