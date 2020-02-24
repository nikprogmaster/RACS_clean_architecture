package com.example.racs.presentation.viewmodel;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.racs.App;
import com.example.racs.model.data.UserPostEntityData;
import com.example.racs.model.data.UsersEntityData;
import com.example.racs.data.repository.UsersRepository;
import com.example.racs.domain.usecases.addusecases.AddUserInteractor;
import com.example.racs.domain.usecases.deleteusecases.DeleteUserInteractor;
import com.example.racs.domain.usecases.getusecases.GetUsersInteractor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UsersViewModel extends ViewModel {

    private static final String ACCESS_TOKEN = "ACCESS";
    private static final int RETRY_COUNT = 5;
    private static final int DEFAULT_NUMBER = 100;

    private MutableLiveData<List<UsersEntityData.User>> usersData;
    private UsersRepository usersRepository;
    private GetUsersInteractor getUsersInteractor;
    private DeleteUserInteractor deleteUserInteractor;
    private AddUserInteractor addUserInteractor;
    private SharedPreferences settings;
    private boolean isLoading = false;
    private boolean added;
    private boolean deleted;
    private int reguestedSize;
    private boolean onStop = false;
    private int pagesCount = 1;
    private List<UsersEntityData.User> list = new ArrayList<>();


    public int getPagesCount() {
        return pagesCount;
    }

    public boolean isOnStop() {
        return onStop;
    }


    public boolean isLoading() {
        return isLoading;
    }

    public UsersViewModel(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public LiveData<List<UsersEntityData.User>> getData() {
        if (usersData == null) {
            usersData = new MutableLiveData<>();
            loadData(DEFAULT_NUMBER);
        }
        return usersData;
    }


    public void loadData(final int count) {
        reguestedSize = count;
        list = new ArrayList<>();
        if (getUsersInteractor == null) {
            settings = App.getSettings();
            getUsersInteractor = new GetUsersInteractor(usersRepository);
        }
        isLoading = true;
        getUsersInteractor.getUsers(settings.getString(ACCESS_TOKEN, ""), count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwableObservable -> throwableObservable
                        .zipWith(Observable.range(1, RETRY_COUNT), (BiFunction<Throwable, Integer, Observable>) (throwable, integer) -> {
                            if (integer < RETRY_COUNT) {
                                list = new ArrayList<>();
                                return Observable.just(0L);
                            } else {
                                return Observable.error(throwable);
                            }
                        }).flatMap((Function<Observable, ObservableSource<?>>) observable -> observable))
                .subscribe(new DisposableObserver<UsersEntityData>() {
                    @Override
                    public void onNext(UsersEntityData usersEntityData) {
                        if (usersEntityData.getUsers() != null) {
                            list.addAll(usersEntityData.getUsers());
                        }
                        Log.i("onNext", String.valueOf(System.currentTimeMillis()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("onError", String.valueOf(System.currentTimeMillis()));
                        e.printStackTrace();

                        isLoading = false;
                    }

                    @Override
                    public void onComplete() {

                        usersData.postValue(list);

                        pagesCount = list.size() % 100 == 0 ? list.size() / 100 : list.size() / 100 + 1;
                        Log.i("getUsers().size()", "= " + list.size());
                        Log.i("onComp", "=" + reguestedSize);
                        if (list.size() < reguestedSize) {
                            Log.i("UserVM", "stop");
                            onStop = true;
                        }

                        isLoading = false;
                    }
                });
    }


    public void deleteUser(int id) {
        if (deleteUserInteractor == null) {
            deleteUserInteractor = new DeleteUserInteractor(usersRepository);
        }
        settings = App.getSettings();
        deleteUserInteractor.deleteUser(settings.getString(ACCESS_TOKEN, ""), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        loadData(pagesCount * 100);
                        deleted = true;
                    }

                    @Override
                    public void onError(Throwable e) {
                        deleted = false;
                    }
                });
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void addUser(UserPostEntityData body) {
        if (addUserInteractor == null) {
            addUserInteractor = new AddUserInteractor(usersRepository);
        }
        isLoading = true;
        settings = App.getSettings();
        addUserInteractor.addUser(settings.getString(ACCESS_TOKEN, ""), body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        loadData(usersData.getValue().size() % 100 == 0 ? usersData.getValue().size() + 1 : pagesCount * 100);
                        isLoading = false;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

}

