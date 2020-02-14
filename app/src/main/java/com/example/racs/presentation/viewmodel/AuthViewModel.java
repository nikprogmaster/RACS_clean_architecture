package com.example.racs.presentation.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.racs.data.api.App;
import com.example.racs.data.entities.AuthEntity;
import com.example.racs.data.entities.AuthPostEntity;
import com.example.racs.data.repository.AuthRepository;
import com.example.racs.domain.usecases.Authorize;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.Timer;
import java.util.TimerTask;

public class AuthViewModel extends AndroidViewModel implements OnReceiverSecretData {


    private MutableLiveData<AuthEntity> authData;
    private MutableLiveData<AuthPostEntity> authPostData;
    private MutableLiveData<Boolean> firstStarted;
    private boolean isFirstStarted = false;
    private AuthRepository authRepository = new AuthRepository();
    private SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String REFRESH_TOKEN = "REFRESH";
    private static final String APP_PREFERENCES = "mysettings";
    private static final String LOGIN_KEY = "LOGIN";
    private static final String PASSWORD_KEY = "PASSWORD";
    private static Authorize authorize;
    private static String access;
    private static final long ACCESS_TOKEN_LIFETIME = 300000; // в миллисекундах, в минутах = 5 мин
    private static final long LIFETIME = 240000; // в миллисекундах, в минутах = 5 мин
    private static String ACCESS_TOKEN_START = "Bearer ";
    private static final Timer timer = new Timer();
    private final TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            loadData();
        }
    };

    public LiveData<Boolean> getFirstStarted() {
        if(firstStarted == null){
            firstStarted = new MutableLiveData<>();
            firstStarted.setValue(false);
        }
        return firstStarted;
    }

    public AuthViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<AuthEntity> getData() {
        if (authData == null) {
            authData = new MutableLiveData<>();
            loadData();
        }
        return authData;
    }


    private void loadData() {
        if (authorize == null) {
            authorize = new Authorize(authRepository, authPostData.getValue(), new OnCompleteListener<AuthEntity>() {
                @Override
                public void onComplete(AuthEntity smt) {
                    if (access == null) {
                        isFirstStarted = true;
                        timer.schedule(timerTask, ACCESS_TOKEN_LIFETIME, ACCESS_TOKEN_LIFETIME);
                    }
                    access = ACCESS_TOKEN_START + smt.getAccess();
                    authData.setValue(smt);
                    settings = getApplication().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(ACCESS_TOKEN, access);
                    editor.putString(REFRESH_TOKEN, smt.getRefresh());
                    editor.apply();
                    if (isFirstStarted){
                        firstStarted.setValue(true);
                        isFirstStarted = false;
                    }
                }
            });
        }

        authorize.authorize();
    }


    @Override
    public void onReceive(String email, String password) {
        if (authPostData == null) {
            authPostData = new MutableLiveData<>();
            authPostData.setValue(new AuthPostEntity(email, password));
            settings = getApplication().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(LOGIN_KEY, email);
            editor.putString(PASSWORD_KEY, password);
            editor.apply();
        }
        authPostData.setValue(new AuthPostEntity(email, password));
    }

    @Override
    public void onGetState(App.ApplicationState state) {

    }


    public LiveData<AuthPostEntity> getLoginPassData() {
        return authPostData;
    }

}
