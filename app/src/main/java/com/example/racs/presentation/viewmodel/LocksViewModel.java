package com.example.racs.presentation.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.repository.LocksRepository;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.domain.usecases.deleteusecases.DeleteLock;
import com.example.racs.domain.usecases.getusecases.GetLocks;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LocksViewModel extends AndroidViewModel {

    private MutableLiveData<List<LocksEntity.Lock>> locksData;
    private LocksRepository locksRepository = new LocksRepository();
    private GetLocks usecaseGetLocks;
    private DeleteLock usecaseDeleteLock;
    private SharedPreferences settings;
    private static final String APP_PREFERENCES = "mysettings";
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final int DEFAULT_NUMBER = 50;
    private OnCompleteListener<List<LocksEntity.Lock>> onCompleteListener;
    private static final long ACCESS_TOKEN_LIFETIME = 300000;
    private static final Timer timer = new Timer();
    private final TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            loadData();
        }
    };

    public LocksViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<LocksEntity.Lock>> getData() {
        if (locksData == null) {
            locksData = new MutableLiveData<>();
            loadData();
        }
        return locksData;
    }

    private void loadData() {
        if (usecaseGetLocks == null){
            settings = getApplication().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            usecaseGetLocks = new GetLocks(locksRepository, settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER, new OnCompleteListener<List<LocksEntity.Lock>>() {
                @Override
                public void onComplete(List<LocksEntity.Lock> smt) {
                    if (locksData == null) {
                        timer.schedule(timerTask, ACCESS_TOKEN_LIFETIME, ACCESS_TOKEN_LIFETIME);
                    }
                    locksData.setValue(smt);
                }
            });
        }
        usecaseGetLocks.getLocks();
    }

    public void deleteLock(){

    }
}
