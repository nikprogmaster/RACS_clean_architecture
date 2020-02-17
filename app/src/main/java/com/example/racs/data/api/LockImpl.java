package com.example.racs.data.api;

import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.repository.datasource.OnReceiveDataListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LockImpl {

    private LockApi lockApi = App.getRetrofit().create(LockApi.class);
    private LocksEntity locksEntity;
    private boolean deleted;

    public void getLocks(String token, int count, final OnReceiveDataListener<LocksEntity> onReceiveDataListener) {

        lockApi.getLocks(token, count).enqueue(new Callback<LocksEntity>() {
            @Override
            public void onResponse(Call<LocksEntity> call, Response<LocksEntity> response) {
                locksEntity = response.body();
                onReceiveDataListener.onReceive(locksEntity);
            }

            @Override
            public void onFailure(Call<LocksEntity> call, Throwable t) {

            }
        });

    }


    public void deleteLock(String token, int id, final OnReceiveDataListener<Boolean> onReceiveDataListener) {

        lockApi.deleteLock(token, id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                deleted = response.code() == 201;
                onReceiveDataListener.onReceive(deleted);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
