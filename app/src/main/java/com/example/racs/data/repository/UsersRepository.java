package com.example.racs.data.repository;

import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.datasource.DataSource;
import com.example.racs.data.repository.datasource.DataSourceFactory;

import java.util.List;

public class UsersRepository implements IUsersReposytory {

    private DataSource dataSource = DataSourceFactory.getDataSource();

    @Override
    public List<UsersEntity.User> getUsers(String token, int count) {
        return dataSource.usersList(token, count);
    }

    @Override
    public boolean addUser(String token, UserPostEntity body) {
        return dataSource.addUser(token, body);
    }

    @Override
    public boolean deleteUser(String token, int id) {
        return dataSource.deleteUser(token, id);
    }
}
