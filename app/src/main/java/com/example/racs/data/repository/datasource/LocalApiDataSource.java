package com.example.racs.data.repository.datasource;

import android.util.Log;

import com.example.racs.data.api.AccessImpl;
import com.example.racs.data.api.AuthorizationImpl;
import com.example.racs.data.api.LockImpl;
import com.example.racs.data.api.UserImpl;
import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.entities.AuthEntity;
import com.example.racs.data.entities.AuthPostEntity;
import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.entities.UsersEntity;

import java.util.List;

public class LocalApiDataSource implements DataSource {

    private final AccessImpl accessImpl;
    private final AuthorizationImpl authorizationImpl;
    private final LockImpl lockImpl;
    private final UserImpl userImpl;

    public LocalApiDataSource(AccessImpl accessImpl, AuthorizationImpl authorizationImpl, LockImpl lockImpl, UserImpl userImpl) {
        this.accessImpl = accessImpl;
        this.authorizationImpl = authorizationImpl;
        this.lockImpl = lockImpl;
        this.userImpl = userImpl;
    }


    @Override
    public void locksList(String token, int count, final OnReceiveDataListener<List<LocksEntity.Lock>> dataListener) {
        OnReceiveDataListener<LocksEntity> onReceiveDataListener = new OnReceiveDataListener<LocksEntity>() {
            @Override
            public void onReceive(LocksEntity obj) {
                dataListener.onReceive(obj.getResults());
            }
        };
        lockImpl.getLocks(token, count, onReceiveDataListener);
    }


    @Override
    public void usersList(String token, int count, final OnReceiveDataListener<List<UsersEntity.User>> dataListener) {
        OnReceiveDataListener<UsersEntity> onReceiveDataListener = new OnReceiveDataListener<UsersEntity>() {
            @Override
            public void onReceive(UsersEntity obj) {
                if (obj == null){
                    dataListener.onReceive(null);
                } else{
                    dataListener.onReceive(obj.getUsers());
                }
                Log.i("DataSouceImpl", "Ну ок, сюда пришли");
            }
        };
        userImpl.getUsers(token, count, onReceiveDataListener);
    }

    @Override
    public void accessesList(String token, int count, final OnReceiveDataListener<List<AccessEntity.AccPOJO>> dataListener) {
        OnReceiveDataListener<AccessEntity> onReceiveDataListener = new OnReceiveDataListener<AccessEntity>() {
            @Override
            public void onReceive(AccessEntity obj) {
                dataListener.onReceive(obj.getResults());
            }
        };
        accessImpl.getAccesses(token, count, onReceiveDataListener);
    }

    @Override
    public void getAccessesToLock(Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> allusers, final OnReceiveDataListener<List<UsersEntity.User>> dataListener) {
        OnReceiveDataListener<List<UsersEntity.User>> onReceiveDataListener = new OnReceiveDataListener<List<UsersEntity.User>>() {
            @Override
            public void onReceive(List<UsersEntity.User> obj) {
                dataListener.onReceive(obj);
            }
        };
        accessImpl.getAccessesToLock(lockId, accesses, allusers, onReceiveDataListener);
    }

    @Override
    public void addAccess(String token, AccessPostEntity body, final OnReceiveDataListener<Boolean> dataListener) {
        OnReceiveDataListener<Boolean> onReceiveDataListener = new OnReceiveDataListener<Boolean>() {
            @Override
            public void onReceive(Boolean obj) {
                dataListener.onReceive(obj);
            }
        };
        accessImpl.postAccess(token, body, onReceiveDataListener);
    }

    @Override
    public void addUser(String token, UserPostEntity body, final OnReceiveDataListener<Boolean> dataListener) {
        OnReceiveDataListener<Boolean> onReceiveDataListener = new OnReceiveDataListener<Boolean>() {
            @Override
            public void onReceive(Boolean obj) {
                dataListener.onReceive(obj);
            }
        };
        userImpl.addUser(token, body, onReceiveDataListener);
    }

    @Override
    public void deleteAccess(String token, int id, final OnReceiveDataListener<Boolean> dataListener) {
        OnReceiveDataListener<Boolean> onReceiveDataListener = new OnReceiveDataListener<Boolean>() {
            @Override
            public void onReceive(Boolean obj) {
                dataListener.onReceive(obj);
            }
        };
        accessImpl.deleteAccess(token, id, onReceiveDataListener);
    }

    @Override
    public void deleteUser(String token, int id, final OnReceiveDataListener<Boolean> dataListener) {
        OnReceiveDataListener<Boolean> onReceiveDataListener = new OnReceiveDataListener<Boolean>() {
            @Override
            public void onReceive(Boolean obj) {
                dataListener.onReceive(obj);
            }
        };
        userImpl.deleteUser(token, id, onReceiveDataListener);
    }

    @Override
    public void deleteLock(String token, int id, final OnReceiveDataListener<Boolean> dataListener) {
        OnReceiveDataListener<Boolean> onReceiveDataListener = new OnReceiveDataListener<Boolean>() {
            @Override
            public void onReceive(Boolean obj) {
                dataListener.onReceive(obj);
            }
        };
        lockImpl.deleteLock(token, id, onReceiveDataListener);
    }

    @Override
    public AuthEntity getTokens(AuthPostEntity body) {
        return authorizationImpl.authentication(body);
    }

}
