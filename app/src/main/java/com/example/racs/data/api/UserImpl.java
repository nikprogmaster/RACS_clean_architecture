package com.example.racs.data.api;

import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.entities.UsersEntity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class UserImpl {

    private UsersEntity usersEntity;
    private UserApi userApi = App.getRetrofit().create(UserApi.class);
    private boolean deleted;
    private boolean added;

    public UsersEntity getUsers(String token, int count) {
        Call<UsersEntity> call = userApi.getUsers(token, count);
        try {
            Response<UsersEntity> response = call.execute();
            usersEntity = response.body();
            return usersEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersEntity;
    }

    public boolean addUser(String token, UserPostEntity body) {
        Call<UsersEntity> call = userApi.addUser(token, body);
        try {
            Response<UsersEntity> response = call.execute();
            added = response.code() == 201;
            return added;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return added;
    }

    public boolean deleteUser(String token, int id) {
        Call<Response<Void>> call = userApi.deleteUser(token, id);
        try {
            Response<Response<Void>> response = call.execute();
            deleted = response.code() == 201;
            return deleted;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deleted;
    }
}
