package com.example.racs.data.repository;

import androidx.annotation.Nullable;

import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.datasource.DataSource;
import com.example.racs.data.repository.datasource.DataSourceFactory;
import com.example.racs.data.repository.datasource.OnReceiveDataListener;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;

public class AccessesRepository implements IAccessesRepository {

    private DataSource dataSource = DataSourceFactory.getDataSource();


    @Nullable
    @Override
    public void getAccesses(String token, int count, final OnCompleteListener<List<AccessEntity.AccPOJO>> onCompleteListener) {
        OnReceiveDataListener<List<AccessEntity.AccPOJO>> onReceiveDataListener = new OnReceiveDataListener<List<AccessEntity.AccPOJO>>() {
            @Override
            public void onReceive(List<AccessEntity.AccPOJO> obj) {
                onCompleteListener.onComplete(obj);
            }
        };
        dataSource.accessesList(token, count, onReceiveDataListener);
    }

    @Override
    public void addAssess(String token, AccessPostEntity body, final OnCompleteListener<Boolean> onCompleteListener) {
        OnReceiveDataListener<Boolean> onReceiveDataListener = new OnReceiveDataListener<Boolean>() {
            @Override
            public void onReceive(Boolean obj) {
                onCompleteListener.onComplete(obj);
            }
        };
        dataSource.addAccess(token, body, onReceiveDataListener);
    }

    @Override
    public void deleteAccess(String token, int id, final OnCompleteListener<Boolean> onCompleteListener) {
        OnReceiveDataListener<Boolean> onReceiveDataListener = new OnReceiveDataListener<Boolean>() {
            @Override
            public void onReceive(Boolean obj) {
                onCompleteListener.onComplete(obj);
            }
        };
        dataSource.deleteAccess(token, id, onReceiveDataListener);
    }

    @Nullable
    @Override
    public void getAccessesToLock(Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> users, final OnCompleteListener<List<UsersEntity.User>> onCompleteListener) {
        OnReceiveDataListener<List<UsersEntity.User>> onReceiveDataListener = new OnReceiveDataListener<List<UsersEntity.User>>() {
            @Override
            public void onReceive(List<UsersEntity.User> obj) {
                onCompleteListener.onComplete(obj);
            }
        };
        dataSource.getAccessesToLock(lockId, accesses, users, onReceiveDataListener);
    }
}
