package com.example.racs.data.repository;

import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.repository.datasource.DataSource;
import com.example.racs.data.repository.datasource.DataSourceFactory;

import java.util.List;

public class LocksRepository implements ILocksRepository {

    private DataSource dataSource = DataSourceFactory.getDataSource();

    @Override
    public List<LocksEntity.Lock> getLocks(String token, int count) {
        return dataSource.locksList(token, count);
    }

    @Override
    public boolean deleteLock(String token, int id) {
        return dataSource.deleteLock(token, id);
    }
}
