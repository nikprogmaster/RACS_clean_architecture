package com.example.racs.domain.usecases.getusecases;

import android.os.AsyncTask;

import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.repository.IAccessesRepository;
import com.example.racs.domain.usecases.Authorize;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;

public class GetAccesses {

    private IAccessesRepository repository;
    private OnCompleteListener<List<AccessEntity.AccPOJO>> onCompleteListener;

    public GetAccesses(IAccessesRepository repository, OnCompleteListener<List<AccessEntity.AccPOJO>> onCompleteListener) {
        this.repository = repository;
        this.onCompleteListener = onCompleteListener;
    }

    public void getAccesses(String token, int count) {
        repository.getAccesses(token, count, onCompleteListener);
    }



}
