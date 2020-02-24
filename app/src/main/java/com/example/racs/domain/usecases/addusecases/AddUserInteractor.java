package com.example.racs.domain.usecases.addusecases;

import com.example.racs.model.data.UserPostEntityData;
import com.example.racs.data.repository.IUsersReposytory;

import io.reactivex.Completable;

public class AddUserInteractor {

    private final IUsersReposytory repository;

    public AddUserInteractor(IUsersReposytory repository) {
        this.repository = repository;
    }

    public Completable addUser(String token, UserPostEntityData body) {
        return repository.addUser(token, body);
    }


}
