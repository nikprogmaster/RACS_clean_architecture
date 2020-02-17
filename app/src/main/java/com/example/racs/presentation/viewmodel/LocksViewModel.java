package com.example.racs.presentation.viewmodel;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.racs.data.api.App;
import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.repository.LocksRepository;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.domain.usecases.deleteusecases.DeleteLock;
import com.example.racs.domain.usecases.getusecases.GetLocks;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LocksViewModel extends ViewModel {

    private MutableLiveData<List<LocksEntity.Lock>> locksData;
    private LocksRepository locksRepository = new LocksRepository();
    private GetLocks usecaseGetLocks;
    private DeleteLock usecaseDeleteLock;
    private SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final int DEFAULT_NUMBER = 100;
    private OnCompleteListener<List<LocksEntity.Lock>> onCompleteListener;
    private static final long ACCESS_TOKEN_LIFETIME = 300000;
    private static final Timer timer = new Timer();
    private final TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            loadData();
        }
    };


    public LiveData<List<LocksEntity.Lock>> getData() {
        if (locksData == null) {
            locksData = new MutableLiveData<>();
            loadData();
        }
        return locksData;
    }

    public void loadData() {
        if (usecaseGetLocks == null){
            settings = App.getSettings();
            usecaseGetLocks = new GetLocks(locksRepository,  new OnCompleteListener<List<LocksEntity.Lock>>() {
                @Override
                public void onComplete(List<LocksEntity.Lock> smt) {
                    if (locksData == null) {
                        timer.schedule(timerTask, ACCESS_TOKEN_LIFETIME, ACCESS_TOKEN_LIFETIME);
                    }
                    locksData.setValue(smt);
                }
            });
        }
        usecaseGetLocks.getLocks(settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER);
    }

    public void deleteLock(int id){
        if(usecaseDeleteLock == null){
            usecaseDeleteLock = new DeleteLock(locksRepository, new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(Boolean smt) {
                    loadData();
                }
            });
        }
        usecaseDeleteLock.deleteLock(settings.getString(ACCESS_TOKEN, ""), id);
    }
}
