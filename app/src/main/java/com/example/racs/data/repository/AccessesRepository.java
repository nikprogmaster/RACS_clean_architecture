package com.example.racs.data.repository;

import androidx.annotation.Nullable;

import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.datasource.DataSource;
import com.example.racs.data.repository.datasource.DataSourceFactory;

import java.util.List;

public class AccessesRepository implements IAccessesRepository {

    private DataSource dataSource = DataSourceFactory.getDataSource();

    @Nullable
    @Override
    public List<AccessEntity.AccPOJO> getAccesses(String token, int count) {
        return dataSource.accessesList(token, count);
    }

    @Override
    public boolean addAssess(String token, AccessPostEntity body) {
        return dataSource.addAccess(token, body);
    }

    @Override
    public boolean deleteAccess(String token, int id) {
        return dataSource.deleteAccess(token, id);
    }

    @Nullable
    @Override
    public List<UsersEntity.User> getAccessesToLock(Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> users) {
        return dataSource.getAccessesToLock(lockId, accesses, users);
    }
}
