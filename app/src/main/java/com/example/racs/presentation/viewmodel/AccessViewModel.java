package com.example.racs.presentation.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.racs.App;
import com.example.racs.model.data.AccessEntityData;
import com.example.racs.model.data.AccessPostEntityData;
import com.example.racs.model.data.UsersEntityData;
import com.example.racs.data.repository.AccessesRepository;
import com.example.racs.domain.usecases.addusecases.AddAccessInteractor;
import com.example.racs.domain.usecases.deleteusecases.DeleteAccessInteractor;
import com.example.racs.domain.usecases.getusecases.GetAccessesInteractor;
import com.example.racs.domain.usecases.getusecases.GetAccessesToLockInteractor;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AccessViewModel extends ViewModel {

    public static final String ADDED = "ADDED_NEW_VALUE";
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final int DEFAULT_NUMBER = 50;

    private MutableLiveData<List<AccessEntityData.Access>> accessesData;
    private MutableLiveData<List<UsersEntityData.User>> usersOfLockData;
    private final AccessesRepository accessesRepository;
    private GetAccessesInteractor getAccessesInteractor;
    private AddAccessInteractor addAccessInteractor;
    private DeleteAccessInteractor deleteAccessInteractor;
    private GetAccessesToLockInteractor getAccessesToLockInteractor;
    private SharedPreferences settings;
    private boolean deleted;

    public AccessViewModel(AccessesRepository accessesRepository) {
        this.accessesRepository = accessesRepository;
    }

    public MutableLiveData<List<UsersEntityData.User>> getUsersOfLockData() {
        usersOfLockData = new MutableLiveData<>();
        return usersOfLockData;
    }


    public LiveData<List<AccessEntityData.Access>> getData() {
        if (accessesData == null) {
            accessesData = new MutableLiveData<>();
            loadData(DEFAULT_NUMBER);
        }
        return accessesData;
    }

    public void loadData(int count) {
        if (getAccessesInteractor == null) {
            settings = App.getSettings();
            getAccessesInteractor = new GetAccessesInteractor(accessesRepository);
        }
        getAccessesInteractor.getAccesses(settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<AccessEntityData>() {
                    @Override
                    public void onSuccess(AccessEntityData accessEntityData) {
                        accessesData.postValue(accessEntityData.getAccesses());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    public void deleteAccess(int id) {
        if (deleteAccessInteractor == null) {
            deleteAccessInteractor = new DeleteAccessInteractor(accessesRepository);
        }
        deleteAccessInteractor.deleteUser(settings.getString(ACCESS_TOKEN, ""), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        deleted = true;
                        loadData(accessesData.getValue().size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        deleted = false;
                    }
                });
    }

    public void addAccess(AccessPostEntityData body) {
        if (addAccessInteractor == null) {
            addAccessInteractor = new AddAccessInteractor(accessesRepository);
        }
        addAccessInteractor.addAccess(settings.getString(ACCESS_TOKEN, ""), body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        loadData(accessesData.getValue().size() + 1);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void getAccessToLock(int lockId, final List<UsersEntityData.User> users) {
        if (getAccessesToLockInteractor == null) {
            getAccessesToLockInteractor = new GetAccessesToLockInteractor(accessesRepository);
        }
        getAccessesToLockInteractor.getAccessesToLock(lockId, users, accessesData.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<UsersEntityData.User>>() {
                    @Override
                    public void onSuccess(List<UsersEntityData.User> users) {
                        usersOfLockData.postValue(users);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
