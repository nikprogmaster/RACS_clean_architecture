package com.example.racs.data.api;

import android.util.Log;

import com.example.racs.model.data.UsersEntityData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class UserImpl {

    private UserApi userApi;

    public UserImpl(UserApi userApi) {
        this.userApi = userApi;
    }

    public Observable<UsersEntityData> getUsers(final String token, int count) {

        int page, number;
        Log.i("Количество", "=" + count);
        final Map<Integer, Integer> pageMap = new HashMap<>();
        final List<Integer> pageList = new ArrayList<>();
        for (int i = count; i > 0; i -= number) {
            if (i % 100 == 0) {
                page = i / 100;
                number = 100;
            } else {
                page = i / 100 + 1;
                number = i % 100;
            }
            pageMap.put(page, number);
            pageList.add(page);
        }
        Collections.sort(pageList);


        List<Observable<UsersEntityData>> observableList = new ArrayList<>();
        for (int i : pageList) {
            observableList.add(userApi.getUsers(token, i, pageMap.get(i)));
        }
        Log.i("observableList.size", " =" + observableList.size());
        return Observable.concat(observableList);

    }


}
