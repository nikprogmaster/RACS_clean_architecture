package com.example.racs.domain.usecases.addusecases;

import android.os.AsyncTask;

import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.repository.IAccessesRepository;
import com.example.racs.domain.usecases.OnCompleteListener;

public class AddAccess {

    private final IAccessesRepository repository;
    private OnCompleteListener<Boolean> onCompleteListener;

    public AddAccess(IAccessesRepository repository, OnCompleteListener<Boolean> onCompleteListener) {
        this.repository = repository;
        this.onCompleteListener = onCompleteListener;
    }

    public void addAccess(String token, AccessPostEntity body) {
        repository.addAssess(token,body,onCompleteListener);
    }


}
