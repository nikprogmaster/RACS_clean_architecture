package com.example.racs.domain.usecases.getusecases;

import com.example.racs.model.data.UsersEntityData;
import com.example.racs.data.repository.IUsersReposytory;

import io.reactivex.Observable;

public class GetUsersInteractor {

    private IUsersReposytory repository;

    public GetUsersInteractor(IUsersReposytory repository) {
        this.repository = repository;
    }


    public Observable<UsersEntityData> getUsers(String token, int count) {
        return repository.getUsers(token, count);
    }


}
