package com.example.racs.data.repository;

import androidx.annotation.Nullable;

import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.entities.UsersEntity;

import java.util.List;

public interface IAccessesRepository {

    @Nullable
    List<AccessEntity.AccPOJO> getAccesses(String token, int count);

    boolean addAssess(String token, AccessPostEntity body);

    boolean deleteAccess(String token, int id);

    @Nullable
    List<UsersEntity.User> getAccessesToLock(Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> users);
}
