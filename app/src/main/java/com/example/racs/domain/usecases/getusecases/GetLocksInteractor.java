package com.example.racs.domain.usecases.getusecases;

import com.example.racs.model.data.LocksEntityData;
import com.example.racs.data.repository.ILocksRepository;

import io.reactivex.Single;

public class GetLocksInteractor {

    private ILocksRepository repository;

    public GetLocksInteractor(ILocksRepository repository) {
        this.repository = repository;
    }

    public Single<LocksEntityData> getLocks(String token, int count) {
        return repository.getLocks(token, count);
    }


}
