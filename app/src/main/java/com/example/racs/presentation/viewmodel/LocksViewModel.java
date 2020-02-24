package com.example.racs.presentation.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.racs.App;
import com.example.racs.model.data.LocksEntityData;
import com.example.racs.data.repository.LocksRepository;
import com.example.racs.domain.usecases.deleteusecases.DeleteLockInteractor;
import com.example.racs.domain.usecases.getusecases.GetLocksInteractor;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LocksViewModel extends ViewModel {

    private static final String ACCESS_TOKEN = "ACCESS";
    private static final long ACCESS_TOKEN_LIFETIME = 300000;
    private static final int DEFAULT_NUMBER = 100;

    private MutableLiveData<List<LocksEntityData.Lock>> locksData;
    private GetLocksInteractor getLocksInteractor;
    private DeleteLockInteractor deleteLockInteractor;
    private SharedPreferences settings;
    private LocksRepository locksRepository;

    public LocksViewModel(LocksRepository locksRepository) {
        this.locksRepository = locksRepository;
    }

    public LiveData<List<LocksEntityData.Lock>> getData() {
        if (locksData == null) {
            locksData = new MutableLiveData<>();
            loadData();
        }
        return locksData;
    }

    public void loadData() {
        if (getLocksInteractor == null) {
            getLocksInteractor = new GetLocksInteractor(locksRepository);
        }
        settings = App.getSettings();
        getLocksInteractor.getLocks(settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<LocksEntityData>() {
                    @Override
                    public void onSuccess(LocksEntityData locksEntityData) {
                        locksData.setValue(locksEntityData.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }

    public void deleteLock(int id) {
        if (deleteLockInteractor == null) {
            deleteLockInteractor = new DeleteLockInteractor(locksRepository);
        }
        deleteLockInteractor.deleteLock(settings.getString(ACCESS_TOKEN, ""), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        loadData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }
}
