package com.example.racs.data.api;

import com.example.racs.data.entities.LocksEntity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class LockImpl {

    private LockApi lockApi = App.getRetrofit().create(LockApi.class);
    private LocksEntity locksEntity;
    private boolean deleted;

    public LocksEntity getLocks(String token, int count) {
        Call<LocksEntity> call = lockApi.getLocks(token, count);
        try {
            Response<LocksEntity> response = call.execute();
            locksEntity = response.body();
            return locksEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return locksEntity;

    }


    public boolean deleteLock(String token, int id) {
        Call<Void> call = lockApi.deleteLock(token, id);
        try {
            Response<Void> response = call.execute();
            deleted = response.code() == 201;
            return deleted;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deleted;
    }
}
