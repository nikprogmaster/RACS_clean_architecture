package com.example.racs.data.api;

import com.example.racs.App;
import com.example.racs.model.data.UsersEntityData;

import java.util.List;

public class UserIm {

    private UsersEntityData usersEntityData;
    private UserApi userApi = App.getRetrofit().create(UserApi.class);
    private boolean deleted;
    private boolean added;
    private int iter;
    private boolean loading;
    private int allnumber;
    private List<Integer> pageList;

    /*
    public void getUsers(String token, int count, final OnReceiveDataListener<UsersEntityData> onReceiveDataListener) {
        int page, number;
        Log.i("Количество", "=" + count);
        final Map<Integer, Integer> pageMap = new HashMap<>();
        pageList = new ArrayList<>();
        for (int i = count; i > 0; i -= number) {
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
        usersEntityData = new UsersEntityData();

        iter = 0;
        Executor executor = new Executor() {
            @Override
            public void execute(Runnable command) {
                new Thread(command).start();
            }
        };

        RunnableTask runnableTask = new RunnableTask(token, pageMap, pageList, onReceiveDataListener);
        executor.execute(runnableTask);


    }

    private class RunnableTask implements Runnable {

        private String token;
        private Map<Integer, Integer> pageMap;
        private List<Integer> pageList;
        private OnReceiveDataListener<UsersEntityData> onReceiveDataListener;

        public RunnableTask(String token, Map<Integer, Integer> pageMap, List<Integer> pageList, OnReceiveDataListener<UsersEntityData> onReceiveDataListener) {
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
            while (iter < pageList.size()) {

                if (!loading) {
                    OnReceiveDataListener<UsersEntityData> dataListener = new OnReceiveDataListener<UsersEntityData>() {
                        @Override
                        public void onReceive(UsersEntityData obj) {
                            if (obj != null) {
                                usersEntityData.getUsers().addAll(obj.getUsers());
                            }
                            Log.i("В onRecieve", "хоть сюда попали");
                            Log.i("iter", "= " + iter);
                            iter++;
                            loading = false;
                            Log.i("pageList.size()", " =" + pageList.size());
                            if (iter == pageList.size()) {
                                loading = true;
                                Log.i("В конце цикла", "onRecieve");
                                Log.i("usersEntityData", " = " + usersEntityData.getUsers().size());
                                Log.i("allnumber", " =" + allnumber);
                                if (usersEntityData.getUsers().size() < allnumber - 99) {
                                    onReceiveDataListener.onReceive(null);
                                } else {
                                    onReceiveDataListener.onReceive(usersEntityData);
                                }
                            }

                        }
                    };
                    loading = true;
                    Log.i("UserIm", "Иду в сеть");
                    if (iter < pageList.size()) {
                        int page = pageList.get(iter);
                        int number = pageMap.get(page);
                        allnumber = allnumber + number;
                        getPartUsers(token, page, number, dataListener);
                    }
                }
            }
        }
    }

    private void getPartUsers(String token, int page, int number, final OnReceiveDataListener<UsersEntityData> onReceiveDataListener) {
        *//*Call<UsersEntityData> call = userApi.getUsers(token, page, number);
        try {
            Response<UsersEntityData> response = call.execute();
            if (response.code() == 404){
                    iter = pageList.size() - 1;
                    Log.i("404", "здесь мы");
                }
            onReceiveDataListener.onReceive(response.body());
        } catch (IOException e) {
            e.printStackTrace();
            onReceiveDataListener.onReceive(null);
        }*//*
        userApi.getUsers(token, page, number).enqueue(new Callback<UsersEntityData>() {
            @Override
            public void onResponse(Call<UsersEntityData> call, Response<UsersEntityData> response) {
                if (response.code() == 404) {
                    iter = pageList.size() - 1;
                    Log.i("404", "здесь мы");
                }
                onReceiveDataListener.onReceive(response.body());

            }

            @Override
            public void onFailure(Call<UsersEntityData> call, Throwable t) {
                onReceiveDataListener.onReceive(null);
                t.printStackTrace();
            }
        });
    }



    public void addUser(String token, UserPostEntityData body, final OnReceiveDataListener<Boolean> onReceiveDataListener) {
        userApi.addUser(token, body).enqueue(new Callback<UsersEntityData>() {
            @Override
            public void onResponse(Call<UsersEntityData> call, Response<UsersEntityData> response) {
                added = response.code() == 201 || response.code() == 200;
                onReceiveDataListener.onReceive(added);
            }

            @Override
            public void onFailure(Call<UsersEntityData> call, Throwable t) {
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
    }*/
}
