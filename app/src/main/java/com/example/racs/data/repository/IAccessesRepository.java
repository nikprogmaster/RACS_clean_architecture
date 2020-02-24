package com.example.racs.data.repository;

import androidx.annotation.Nullable;

import com.example.racs.model.data.AccessEntityData;
import com.example.racs.model.data.AccessPostEntityData;
import com.example.racs.model.data.UsersEntityData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IAccessesRepository {

    @Nullable
    Single<AccessEntityData> getAccesses(String token, int count);

    Completable addAssess(String token, AccessPostEntityData body);

    Completable deleteAccess(String token, int id);

    @Nullable
    Single<ArrayList<UsersEntityData.User>> getAccessesToLock(Integer lockId, List<AccessEntityData.Access> accesses, List<UsersEntityData.User> users);
}
