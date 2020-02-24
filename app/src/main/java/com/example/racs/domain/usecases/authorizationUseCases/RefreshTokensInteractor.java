package com.example.racs.domain.usecases.authorizationUseCases;

import com.example.racs.model.data.AuthEntityData;
import com.example.racs.model.data.RefreshEntityData;
import com.example.racs.data.repository.IAuthRepository;

import io.reactivex.Observable;

public class RefreshTokensInteractor {

    private IAuthRepository repository;
    private RefreshEntityData body;

    public RefreshTokensInteractor(IAuthRepository repository, RefreshEntityData body) {
        this.repository = repository;
        this.body = body;
    }

    public Observable<AuthEntityData> refreshTokens() {
        return repository.refreshTokens(body);
    }
}
