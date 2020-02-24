package com.example.racs.domain.usecases.addusecases;

import com.example.racs.model.data.AccessPostEntityData;
import com.example.racs.data.repository.IAccessesRepository;

import io.reactivex.Completable;

public class AddAccessInteractor {

    private final IAccessesRepository repository;

    public AddAccessInteractor(IAccessesRepository repository) {
        this.repository = repository;
    }

    public Completable addAccess(String token, AccessPostEntityData body) {
        return repository.addAssess(token, body);
    }


}
