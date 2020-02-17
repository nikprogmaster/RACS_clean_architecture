package com.example.racs.data.repository;

import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.repository.datasource.DataSource;
import com.example.racs.data.repository.datasource.DataSourceFactory;
import com.example.racs.data.repository.datasource.OnReceiveDataListener;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;

public class LocksRepository implements ILocksRepository {

    private DataSource dataSource = DataSourceFactory.getDataSource();

    @Override
    public void getLocks(String token, int count, final OnCompleteListener<List<LocksEntity.Lock>> onCompleteListener) {
        OnReceiveDataListener<List<LocksEntity.Lock>> onReceiveDataListener = new OnReceiveDataListener<List<LocksEntity.Lock>>() {
            @Override
            public void onReceive(List<LocksEntity.Lock> obj) {
                onCompleteListener.onComplete(obj);
            }
        };
        dataSource.locksList(token, count, onReceiveDataListener);
    }

    @Override
    public void deleteLock(String token, int id, final OnCompleteListener<Boolean> onCompleteListener) {
        OnReceiveDataListener<Boolean> onReceiveDataListener = new OnReceiveDataListener<Boolean>() {
            @Override
            public void onReceive(Boolean obj) {
                onCompleteListener.onComplete(obj);
            }
        };
        dataSource.deleteLock(token, id, onReceiveDataListener);
    }
}
