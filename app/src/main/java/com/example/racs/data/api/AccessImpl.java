package com.example.racs.data.api;

import android.util.Log;

import androidx.annotation.Nullable;

import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.datasource.OnReceiveDataListener;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessImpl {

    private AccessEntity accessEntity;
    private AccessApi accessApi = App.getRetrofit().create(AccessApi.class);
    private boolean deleted;
    private boolean added;

    @Nullable
    public void getAccesses(String token, int count, final OnReceiveDataListener<AccessEntity> onReceiveDataListener) {
        accessApi.getAccesses(token, count).enqueue(new Callback<AccessEntity>() {
            @Override
            public void onResponse(Call<AccessEntity> call, Response<AccessEntity> response) {
                accessEntity = response.body();
                onReceiveDataListener.onReceive(accessEntity);
            }

            @Override
            public void onFailure(Call<AccessEntity> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void postAccess(String token, AccessPostEntity body, final OnReceiveDataListener<Boolean> onReceiveDataListener) {
       accessApi.postAccess(token, body).enqueue(new Callback<AccessEntity>() {
           @Override
           public void onResponse(Call<AccessEntity> call, Response<AccessEntity> response) {
               added = response.code() == 201 || response.code() == 200;
               onReceiveDataListener.onReceive(added);
           }

           @Override
           public void onFailure(Call<AccessEntity> call, Throwable t) {

           }
       });

    }

    public void deleteAccess(String token, int id, final OnReceiveDataListener<Boolean> onReceiveDataListener) {
        accessApi.deleteAccess(token, id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                deleted = response.code() == 201 || response.code() == 200;
                onReceiveDataListener.onReceive(added);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


    public void getAccessesToLock(Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> allusers, final OnReceiveDataListener<List<UsersEntity.User>> dataListener) {
        if (accessEntity != null) {
            OnReceiveDataListener<List<UsersEntity.User>> onReceiveDataListener = new OnReceiveDataListener<List<UsersEntity.User>>() {
                @Override
                public void onReceive(List<UsersEntity.User> obj) {
                    dataListener.onReceive(obj);
                }
            };
            accessEntity.searchUsersByLock(lockId, accesses, allusers, onReceiveDataListener);
        }
    }

}
