package com.example.racs.data.repository;

import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.datasource.OnReceiveDataListener;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;

public interface IUsersReposytory {

    void getUsers(String token, int count, OnCompleteListener<List<UsersEntity.User>> dataListener);

    void addUser(String token, UserPostEntity body, OnCompleteListener<Boolean> onCompleteListener);

    void deleteUser(String token, int id, OnCompleteListener<Boolean> onCompleteListener);

}
