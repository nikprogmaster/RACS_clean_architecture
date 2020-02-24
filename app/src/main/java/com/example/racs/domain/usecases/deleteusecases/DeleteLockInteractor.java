package com.example.racs.domain.usecases.deleteusecases;

import com.example.racs.data.repository.ILocksRepository;

import io.reactivex.Completable;

public class DeleteLockInteractor {

    private ILocksRepository repository;

    public DeleteLockInteractor(ILocksRepository repository) {
        this.repository = repository;
    }

    public Completable deleteLock(String token, int id) {
        return repository.deleteLock(token, id);
    }


}
