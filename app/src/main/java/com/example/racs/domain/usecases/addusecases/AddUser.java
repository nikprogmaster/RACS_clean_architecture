package com.example.racs.domain.usecases.addusecases;

import android.os.AsyncTask;

import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.repository.IUsersReposytory;
import com.example.racs.domain.usecases.OnCompleteListener;

public class AddUser {

    private final IUsersReposytory repository;
    private OnCompleteListener<Boolean> onCompleteListener;

    public AddUser(IUsersReposytory repository, OnCompleteListener<Boolean> onCompleteListener) {
        this.repository = repository;
        this.onCompleteListener = onCompleteListener;
    }

    public void addUser(String token, UserPostEntity body) {
        repository.addUser(token, body, onCompleteListener);
    }


}
