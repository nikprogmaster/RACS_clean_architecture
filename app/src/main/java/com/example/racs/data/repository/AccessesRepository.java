package com.example.racs.data.repository;

import androidx.annotation.Nullable;

import com.example.racs.data.api.AccessApi;
import com.example.racs.data.mappers.AccessEntityMapper;
import com.example.racs.model.data.AccessEntityData;
import com.example.racs.model.data.AccessPostEntityData;
import com.example.racs.model.data.UsersEntityData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class AccessesRepository implements IAccessesRepository {


    private AccessApi accessApi;

    public AccessesRepository(AccessApi accessApi) {
        this.accessApi = accessApi;
    }

    @Nullable
    @Override
    public Single<AccessEntityData> getAccesses(String token, int count) {
        return accessApi.getAccesses(token, count);
    }

    @Override
    public Completable addAssess(String token, AccessPostEntityData body) {
        return accessApi.postAccess(token, body);
    }

    @Override
    public Completable deleteAccess(String token, int id) {
        return accessApi.deleteAccess(token, id);
    }

    @Nullable
    @Override
    public Single<ArrayList<UsersEntityData.User>> getAccessesToLock(Integer lockId, List<AccessEntityData.Access> accesses, List<UsersEntityData.User> users) {
        return AccessEntityData.searchUsersByLock(lockId, accesses, users);
    }
}
