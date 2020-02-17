package com.example.racs.data.repository;

import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.datasource.DataSource;
import com.example.racs.data.repository.datasource.DataSourceFactory;
import com.example.racs.data.repository.datasource.OnReceiveDataListener;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;

public class UsersRepository implements IUsersReposytory {

    private DataSource dataSource = DataSourceFactory.getDataSource();

    @Override
    public void getUsers(String token, int count, final OnCompleteListener<List<UsersEntity.User>> dataListener) {
        OnReceiveDataListener<List<UsersEntity.User>> onReceiveDataListener = new OnReceiveDataListener<List<UsersEntity.User>>() {
            @Override
            public void onReceive(List<UsersEntity.User> obj) {
                dataListener.onComplete(obj);
            }
        };
        dataSource.usersList(token, count, onReceiveDataListener);
    }

    @Override
    public void addUser(String token, UserPostEntity body, final OnCompleteListener<Boolean> onCompleteListener) {
        OnReceiveDataListener<Boolean> onReceiveDataListener = new OnReceiveDataListener<Boolean>() {
            @Override
            public void onReceive(Boolean obj) {
                onCompleteListener.onComplete(obj);
            }
        };
        dataSource.addUser(token, body, onReceiveDataListener);
    }

    @Override
    public void deleteUser(String token, int id, final OnCompleteListener<Boolean> onCompleteListener) {
        OnReceiveDataListener<Boolean> onReceiveDataListener = new OnReceiveDataListener<Boolean>() {
            @Override
            public void onReceive(Boolean obj) {
                onCompleteListener.onComplete(obj);
            }
        };
        dataSource.deleteUser(token, id, onReceiveDataListener);
    }
}
