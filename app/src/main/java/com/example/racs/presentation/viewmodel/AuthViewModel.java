package com.example.racs.presentation.viewmodel;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.racs.App;
import com.example.racs.model.data.AuthEntityData;
import com.example.racs.model.data.AuthPostEntityData;
import com.example.racs.model.data.RefreshEntityData;
import com.example.racs.data.repository.AuthRepository;
import com.example.racs.domain.usecases.authorizationUseCases.GetTokensInteractor;
import com.example.racs.domain.usecases.authorizationUseCases.RefreshTokensInteractor;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel implements OnReceiverSecretData {

    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String REFRESH_TOKEN = "REFRESH";
    private static final String LOGIN_KEY = "LOGIN";
    private static final String PASSWORD_KEY = "PASSWORD";
    private static String ACCESS_TOKEN_START = "Bearer ";
    private static final long ACCESS_TOKEN_LIFETIME = 300000; // в миллисекундах, в минутах = 5 мин

    private MutableLiveData<AuthEntityData> authData;
    private MutableLiveData<AuthPostEntityData> authPostData;
    private MutableLiveData<Boolean> firstStarted;
    private AuthRepository authRepository;
    private SharedPreferences settings;
    private static GetTokensInteractor getTokensInteractor;
    private static RefreshTokensInteractor refreshTokensInteractor;
    private static String access;
    private static final Timer timer = new Timer();
    private final TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            refreshData();
        }
    };

    public AuthViewModel(@NonNull AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<Boolean> getFirstStarted() {
        if (firstStarted == null) {
            firstStarted = new MutableLiveData<>();
            firstStarted.setValue(false);
        }
        return firstStarted;
    }

    public LiveData<AuthEntityData> getData() {
        if (authData == null) {
            authData = new MutableLiveData<>();
            loadData();
        }
        return authData;
    }


    private void loadData() {
        if (getTokensInteractor == null) {
            getTokensInteractor = new GetTokensInteractor(authRepository, authPostData.getValue());
        }
        getTokensInteractor.getTokens()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<AuthEntityData>() {
                    @Override
                    public void onSuccess(AuthEntityData authEntityData) {
                        access = ACCESS_TOKEN_START + authEntityData.getAccess();
                        authData.setValue(authEntityData);
                        settings = App.getSettings();
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(ACCESS_TOKEN, access);
                        editor.putString(REFRESH_TOKEN, authEntityData.getRefresh());
                        editor.apply();
                        firstStarted.setValue(true);
                        timer.schedule(timerTask, ACCESS_TOKEN_LIFETIME);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    private void refreshData() {
        if (refreshTokensInteractor == null) {
            settings = App.getSettings();
            refreshTokensInteractor = new RefreshTokensInteractor(authRepository, new RefreshEntityData(settings.getString(REFRESH_TOKEN, "")));
        }
        refreshTokensInteractor.refreshTokens()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen(objectObservable -> objectObservable.delay(5, TimeUnit.MINUTES))
                .subscribe(new DisposableObserver<AuthEntityData>() {
                    @Override
                    public void onNext(AuthEntityData authEntityData) {
                        access = ACCESS_TOKEN_START + authEntityData.getAccess();
                        settings = App.getSettings();
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(ACCESS_TOKEN, access);
                        editor.apply();
                        Log.i("refreshData", "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public void onReceive(String email, String password) {
        if (authPostData == null) {
            authPostData = new MutableLiveData<>();
            authPostData.setValue(new AuthPostEntityData(email, password));
            settings = App.getSettings();
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(LOGIN_KEY, email);
            editor.putString(PASSWORD_KEY, password);
            editor.apply();
        }
        authPostData.setValue(new AuthPostEntityData(email, password));
    }


    public LiveData<AuthPostEntityData> getLoginPassData() {
        return authPostData;
    }

}
