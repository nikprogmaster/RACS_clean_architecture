package com.example.racs.domain.usecases.deleteusecases;

import android.os.AsyncTask;

import com.example.racs.data.repository.ILocksRepository;
import com.example.racs.domain.usecases.OnCompleteListener;

public class DeleteLock {

    private ILocksRepository repository;
    private OnCompleteListener<Boolean> onCompleteListener;

    public DeleteLock(ILocksRepository repository, OnCompleteListener<Boolean> onCompleteListener) {
        this.repository = repository;
        this.onCompleteListener = onCompleteListener;
    }

    public void deleteLock(String token, int id) {
        repository.deleteLock(token, id, onCompleteListener);
    }


}
