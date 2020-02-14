package com.example.racs.data.api;

import androidx.annotation.Nullable;

import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.entities.UsersEntity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AccessImpl {

    private AccessEntity accessEntity;
    private AccessApi accessApi = App.getRetrofit().create(AccessApi.class);
    private boolean deleted;
    private boolean added;

    @Nullable
    public AccessEntity getAccesses(String token, int n) {
        Call<AccessEntity> call = accessApi.getAccesses(token, n);
        try {
            Response<AccessEntity> response = call.execute();
            accessEntity = response.body();
            return accessEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessEntity;
    }

    public boolean postAccess(String token, AccessPostEntity body) {
        Call<AccessEntity> call = accessApi.postAccess(token, body);
        try {
            Response<AccessEntity> response = call.execute();
            added = response.code() == 201;
            return added;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return added;
    }

    public boolean deleteAccess(String token, int id) {
        Call<Void> call = accessApi.deleteAccess(token, id);
        try {
            Response<Void> response = call.execute();
            deleted = response.code() == 201;
            return deleted;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deleted;
    }

    @Nullable
    public List<UsersEntity.User> getAccessesToLock(Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> allusers) {
        if (accessEntity != null) {
            return accessEntity.searchUsersByLock(lockId, accesses, allusers);
        } else return null;
    }

}
