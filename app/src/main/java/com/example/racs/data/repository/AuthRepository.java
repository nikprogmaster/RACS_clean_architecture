package com.example.racs.data.repository;

import com.example.racs.data.entities.AuthEntity;
import com.example.racs.data.entities.AuthPostEntity;
import com.example.racs.data.repository.datasource.DataSource;
import com.example.racs.data.repository.datasource.DataSourceFactory;

public class AuthRepository implements IAuthRepository {

    DataSource dataSource = DataSourceFactory.getDataSource();

    @Override
    public AuthEntity getTokens(AuthPostEntity body) {
        return dataSource.getTokens(body);
    }

}
