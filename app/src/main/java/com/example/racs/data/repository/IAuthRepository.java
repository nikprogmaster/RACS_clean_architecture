package com.example.racs.data.repository;

import com.example.racs.model.data.AuthEntityData;
import com.example.racs.model.data.AuthPostEntityData;
import com.example.racs.model.data.RefreshEntityData;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface IAuthRepository {

    Single<AuthEntityData> getTokens(AuthPostEntityData body);

    Observable<AuthEntityData> refreshTokens(RefreshEntityData body);

}