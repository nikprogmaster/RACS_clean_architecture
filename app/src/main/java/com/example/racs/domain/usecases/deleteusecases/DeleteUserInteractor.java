package com.example.racs.domain.usecases.deleteusecases;

import com.example.racs.data.repository.IUsersReposytory;

import io.reactivex.Completable;

public class DeleteUserInteractor {

    private IUsersReposytory repository;

    public DeleteUserInteractor(IUsersReposytory repository) {
        this.repository = repository;
    }

    public Completable deleteUser(String token, int id) {
        return repository.deleteUser(token, id);
    }


}
