package com.example.racs.data.repository;

import com.example.racs.model.data.UserPostEntityData;
import com.example.racs.model.data.UsersEntityData;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface IUsersReposytory {

    Observable<UsersEntityData> getUsers(String token, int count);

    Completable addUser(String token, UserPostEntityData body);

    Completable deleteUser(String token, int id);

}
