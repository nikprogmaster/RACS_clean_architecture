package com.example.racs.data.repository;

import androidx.annotation.Nullable;

import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;

public interface IAccessesRepository {

    @Nullable
    void getAccesses(String token, int count, OnCompleteListener<List<AccessEntity.AccPOJO>> onCompleteListener);

    void addAssess(String token, AccessPostEntity body, OnCompleteListener<Boolean> onCompleteListener);

    void deleteAccess(String token, int id, OnCompleteListener<Boolean> onCompleteListener);

    @Nullable
    void getAccessesToLock(Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> users, OnCompleteListener<List<UsersEntity.User>> onCompleteListener);
}
