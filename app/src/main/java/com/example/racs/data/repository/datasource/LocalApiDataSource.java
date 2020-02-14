package com.example.racs.data.repository.datasource;

import com.example.racs.data.api.AccessImpl;
import com.example.racs.data.api.AuthorizationImpl;
import com.example.racs.data.api.LockImpl;
import com.example.racs.data.api.UserImpl;
import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.entities.AuthEntity;
import com.example.racs.data.entities.AuthPostEntity;
import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.entities.RefreshEntity;
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
    public List<LocksEntity.Lock> locksList(String token, int count) {
        return lockImpl.getLocks(token, count).getResults();
    }


    @Override
    public List<UsersEntity.User> usersList(String token, int count) {
        return userImpl.getUsers(token, count).getUsers();
    }

    @Override
    public List<AccessEntity.AccPOJO> accessesList(String token, int count) {
        return accessImpl.getAccesses(token, count).getResults();
    }

    @Override
    public List<UsersEntity.User> getAccessesToLock(Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> allusers) {
        return accessImpl.getAccessesToLock(lockId, accesses, allusers);
    }

    @Override
    public boolean addAccess(String token, AccessPostEntity body) {
        return accessImpl.postAccess(token, body);
    }

    @Override
    public boolean addUser(String token, UserPostEntity body) {
        return userImpl.addUser(token, body);
    }

    @Override
    public boolean deleteAccess(String token, int id) {
        return accessImpl.deleteAccess(token, id);
    }

    @Override
    public boolean deleteUser(String token, int id) {
        return userImpl.deleteUser(token, id);
    }

    @Override
    public boolean deleteLock(String token, int id) {
        return lockImpl.deleteLock(token, id);
    }

    @Override
    public AuthEntity getTokens(AuthPostEntity body) {
        return authorizationImpl.authentication(body);
    }

}
