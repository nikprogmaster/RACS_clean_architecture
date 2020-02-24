package com.example.racs.data.repository;

import com.example.racs.data.api.LockApi;
import com.example.racs.model.data.LocksEntityData;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocksRepository implements ILocksRepository {

    private LockApi lockApi;

    public LocksRepository(LockApi lockApi) {
        this.lockApi = lockApi;
    }

    @Override
    public Single<LocksEntityData> getLocks(String token, int count) {
        return lockApi.getLocks(token, count);
    }

    @Override
    public Completable deleteLock(String token, int id) {
        return lockApi.deleteLock(token, id);
    }
}
