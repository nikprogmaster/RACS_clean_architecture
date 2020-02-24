package com.example.racs.domain.usecases.authorizationUseCases;

import com.example.racs.model.data.AuthEntityData;
import com.example.racs.model.data.AuthPostEntityData;
import com.example.racs.data.repository.IAuthRepository;

import io.reactivex.Single;

public class GetTokensInteractor {

    private IAuthRepository repository;
    private AuthPostEntityData body;

    public GetTokensInteractor(IAuthRepository repository, AuthPostEntityData body) {
        this.repository = repository;
        this.body = body;

    }

    public Single<AuthEntityData> getTokens() {
        return repository.getTokens(body);
    }


}
