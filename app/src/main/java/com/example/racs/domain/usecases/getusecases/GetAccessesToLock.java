package com.example.racs.domain.usecases.getusecases;

import android.os.AsyncTask;

import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.IAccessesRepository;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;

public class GetAccessesToLock {

    private IAccessesRepository repository;
    private Integer lockId;
    private List<AccessEntity.AccPOJO> accesses;
    private List<UsersEntity.User> users;
    private OnCompleteListener<List<UsersEntity.User>> onCompleteListener;

    public GetAccessesToLock(IAccessesRepository repository, Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> users, OnCompleteListener<List<UsersEntity.User>> onCompleteListener) {
        this.repository = repository;
        this.lockId = lockId;
        this.accesses = accesses;
        this.users = users;
        this.onCompleteListener = onCompleteListener;

    }

    public void getAccessesToLock(){
        repository.getAccessesToLock(lockId, accesses, users, onCompleteListener);
    }


}
