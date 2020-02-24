package com.example.racs.domain.usecases.getusecases;

import com.example.racs.model.data.AccessEntityData;
import com.example.racs.data.repository.IAccessesRepository;

import io.reactivex.Single;

public class GetAccessesInteractor {

    private IAccessesRepository repository;

    public GetAccessesInteractor(IAccessesRepository repository) {
        this.repository = repository;
    }

    public Single<AccessEntityData> getAccesses(String token, int count) {
        return repository.getAccesses(token, count);
    }


}
