package com.example.racs.data.repository;

import com.example.racs.data.api.UserApi;
import com.example.racs.data.api.UserImpl;
import com.example.racs.model.data.UserPostEntityData;
import com.example.racs.model.data.UsersEntityData;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class UsersRepository implements IUsersReposytory {

    private UserApi userApi;
    private UserImpl userImpl;

    public UsersRepository(UserApi userApi, UserImpl userImpl) {
        this.userApi = userApi;
        this.userImpl = userImpl;
    }

    @Override
    public Observable<UsersEntityData> getUsers(String token, int count) {
        return userImpl.getUsers(token, count);
    }

    @Override
    public Completable addUser(String token, UserPostEntityData body) {
        return userApi.addUser(token, body);
    }

    @Override
    public Completable deleteUser(String token, int id) {
        return userApi.deleteUser(token, id);
    }
}
