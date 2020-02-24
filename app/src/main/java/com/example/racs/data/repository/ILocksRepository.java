package com.example.racs.data.repository;

import com.example.racs.model.data.LocksEntityData;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ILocksRepository {

    Single<LocksEntityData> getLocks(String token, int count);

    Completable deleteLock(String token, int id);
}
