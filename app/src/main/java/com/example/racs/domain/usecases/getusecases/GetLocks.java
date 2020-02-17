package com.example.racs.domain.usecases.getusecases;

import android.os.AsyncTask;

import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.repository.ILocksRepository;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;

public class GetLocks {

    private ILocksRepository repository;
    private OnCompleteListener<List<LocksEntity.Lock>> onCompleteListener;

    public GetLocks(ILocksRepository repository, OnCompleteListener<List<LocksEntity.Lock>> onCompleteListener) {
        this.repository = repository;
        this.onCompleteListener = onCompleteListener;
    }

    public void getLocks(String token, int count) {
        repository.getLocks(token, count, onCompleteListener);

    }



}
