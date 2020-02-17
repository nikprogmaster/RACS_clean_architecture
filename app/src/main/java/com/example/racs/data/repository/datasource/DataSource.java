package com.example.racs.data.repository.datasource;

import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.entities.AuthEntity;
import com.example.racs.data.entities.AuthPostEntity;
import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.entities.UsersEntity;

import java.util.List;

public interface DataSource {

    void locksList(String token, int count, OnReceiveDataListener<List<LocksEntity.Lock>> dataListener);

    void usersList(String token, int count, OnReceiveDataListener<List<UsersEntity.User>> dataListener);

    void accessesList(String token, int count, OnReceiveDataListener<List<AccessEntity.AccPOJO>> dataListener);

    void getAccessesToLock(Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> allusers, OnReceiveDataListener<List<UsersEntity.User>> dataListener);

    void addAccess(String token, AccessPostEntity body, OnReceiveDataListener<Boolean> dataListener);

    void addUser(String token, UserPostEntity body, OnReceiveDataListener<Boolean> dataListener);

    void deleteAccess(String token, int id, OnReceiveDataListener<Boolean> dataListener);

    void deleteUser(String token, int id, OnReceiveDataListener<Boolean> dataListener);

    void deleteLock(String token, int id, OnReceiveDataListener<Boolean> onReceiveDataListener);

    AuthEntity getTokens(AuthPostEntity body);

}
