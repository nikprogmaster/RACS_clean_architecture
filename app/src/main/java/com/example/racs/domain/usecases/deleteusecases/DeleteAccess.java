package com.example.racs.domain.usecases.deleteusecases;

import android.os.AsyncTask;

import com.example.racs.data.repository.IAccessesRepository;
import com.example.racs.domain.usecases.OnCompleteListener;

public class DeleteAccess {

    private IAccessesRepository repository;
    private OnCompleteListener<Boolean> onCompleteListener;

    public DeleteAccess(IAccessesRepository repository, OnCompleteListener<Boolean> onCompleteListener) {
        this.repository = repository;
        this.onCompleteListener = onCompleteListener;
    }

    public void deleteUser(String token, int id) {
        repository.deleteAccess(token, id, onCompleteListener);
    }



}
