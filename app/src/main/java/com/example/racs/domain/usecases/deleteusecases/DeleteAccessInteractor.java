package com.example.racs.domain.usecases.deleteusecases;

import com.example.racs.data.repository.IAccessesRepository;

import io.reactivex.Completable;

public class DeleteAccessInteractor {

    private IAccessesRepository repository;

    public DeleteAccessInteractor(IAccessesRepository repository) {
        this.repository = repository;
    }

    public Completable deleteUser(String token, int id) {
        return repository.deleteAccess(token, id);
    }


}
