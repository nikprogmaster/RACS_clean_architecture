package com.example.racs.data.api;

import android.util.Log;

import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.datasource.OnReceiveDataListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserImpl {

    private UsersEntity usersEntity;
    private UserApi userApi = App.getRetrofit().create(UserApi.class);
    private boolean deleted;
    private boolean added;
    private int iter;
    private boolean loading;
    private int allnumber;

    public void getUsers(String token, int count, final OnReceiveDataListener<UsersEntity> onReceiveDataListener) {
        int page, number;
        Log.i("Количество", "="+count);
        final Map<Integer, Integer> pageMap = new HashMap<>();
        final List<Integer> pageList = new ArrayList<>();
        for(int i = count; i>0; i -= number) {
            if (i % 100 == 0) {
                page = i / 100;
                number = 100;
            } else {
                page = i / 100 + 1;
                number = i % 100;
            }
            allnumber = allnumber + number;
            pageMap.put(page, number);
            pageList.add(page);
        }
        Collections.sort(pageList);
        usersEntity = new UsersEntity();

        iter = 0;
        Executor executor = new Executor() {
            @Override
            public void execute(Runnable command) {
                new Thread(command).start();
            }
        };
        OnReceiveDataListener<UsersEntity> onReceiveDataListener1 = new OnReceiveDataListener<UsersEntity>() {
            @Override
            public void onReceive(UsersEntity obj) {
                Log.i("usersEntity", " = "+usersEntity.getUsers().size());
                Log.i("allnumber", " =" + allnumber);
                if(usersEntity.getUsers().size() < allnumber - 100){
                    onReceiveDataListener.onReceive(null);
                } else{
                    onReceiveDataListener.onReceive(usersEntity);
                }
            }
        };
        RunnableTask runnableTask = new RunnableTask(token, pageMap, pageList, onReceiveDataListener1);
        executor.execute(runnableTask);


    }

    private class RunnableTask implements Runnable{

        private String token;
        private Map<Integer, Integer> pageMap;
        private List<Integer> pageList;
        private OnReceiveDataListener<UsersEntity> onReceiveDataListener;

        public RunnableTask(String token, Map<Integer, Integer> pageMap, List<Integer> pageList, OnReceiveDataListener<UsersEntity> onReceiveDataListener) {
            this.token = token;
            this.pageMap = pageMap;
            this.pageList = pageList;
            this.onReceiveDataListener = onReceiveDataListener;
        }

        @Override
        public void run() {
            iter = 0;
            loading = false;
            allnumber = 0;
            while ( iter < pageList.size()){

                if (!loading){
                    OnReceiveDataListener<UsersEntity> dataListener = new OnReceiveDataListener<UsersEntity>() {
                        @Override
                        public void onReceive(UsersEntity obj) {
                            if(obj != null){
                                usersEntity.getUsers().addAll(obj.getUsers());
                            }
                            Log.i("В onRecieve", "хоть сюда попали");
                            Log.i("iter", "= "+iter);
                            iter++;
                            loading = false;
                            Log.i("pageList.size()", " =" + pageList.size());
                            if (iter == pageList.size()){
                                loading = true;
                                Log.i("В конце цикла", "onRecieve");
                                onReceiveDataListener.onReceive(usersEntity);
                            }

                        }
                    };
                    loading = true;
                    Log.i("UserImpl", "Иду в сеть");
                    if (iter < pageList.size()){
                        int page = pageList.get(iter);
                        int number = pageMap.get(page);
                        allnumber = allnumber + number;
                        getPartUsers(token, page, number, dataListener);
                    }
                }
            }
        }
    }

    private void getPartUsers(String token, int page, int number, final OnReceiveDataListener<UsersEntity> onReceiveDataListener){
        /*Call<UsersEntity> call = userApi.getUsers(token, page, number);
        try {
            Response<UsersEntity> response = call.execute();
            onReceiveDataListener.onReceive(response.body());
        } catch (IOException e) {
            e.printStackTrace();
            onReceiveDataListener.onReceive(null);
        }*/
        userApi.getUsers(token, page, number).enqueue(new Callback<UsersEntity>() {
            @Override
            public void onResponse(Call<UsersEntity> call, Response<UsersEntity> response) {
                onReceiveDataListener.onReceive(response.body());
            }

            @Override
            public void onFailure(Call<UsersEntity> call, Throwable t) {
                onReceiveDataListener.onReceive(null);
                t.printStackTrace();
            }
        });
    }

    public void addUser(String token, UserPostEntity body, final OnReceiveDataListener<Boolean> onReceiveDataListener) {
        userApi.addUser(token, body).enqueue(new Callback<UsersEntity>() {
            @Override
            public void onResponse(Call<UsersEntity> call, Response<UsersEntity> response) {
                added = response.code() == 201 || response.code() == 200;
                onReceiveDataListener.onReceive(added);
            }

            @Override
            public void onFailure(Call<UsersEntity> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void deleteUser(String token, int id, final OnReceiveDataListener<Boolean> onReceiveDataListener) {
        userApi.deleteUser(token, id).enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                deleted = response.code() == 201 || response.code() == 200;
                onReceiveDataListener.onReceive(deleted);
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        Call<Response<Void>> call = userApi.deleteUser(token, id);
    }
}
