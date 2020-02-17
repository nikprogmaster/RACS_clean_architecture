package com.example.racs.domain.usecases.deleteusecases;

import android.os.AsyncTask;

import com.example.racs.data.repository.IUsersReposytory;
import com.example.racs.domain.usecases.OnCompleteListener;

public class DeleteUser {

    private IUsersReposytory repository;
    private OnCompleteListener<Boolean> onCompleteListener;

    public DeleteUser(IUsersReposytory repository, OnCompleteListener<Boolean> onCompleteListener) {
        this.repository = repository;
        this.onCompleteListener = onCompleteListener;
    }

    public void deleteUser(String token, int id) {
        repository.deleteUser(token, id, onCompleteListener);
    }


}
