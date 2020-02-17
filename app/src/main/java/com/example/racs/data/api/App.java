package com.example.racs.data.api;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.racs.presentation.viewmodel.AccessViewModel;
import com.example.racs.presentation.viewmodel.AuthViewModel;
import com.example.racs.presentation.viewmodel.LocksViewModel;
import com.example.racs.presentation.viewmodel.UsersViewModel;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    public static retrofit2.Retrofit retrofit;
    private static final String APP_PREFERENCES = "mysettings";
    private static final String APP_PREFERENCES_IP = "IP";
    private static SharedPreferences settings;
    private static AuthViewModel authViewModel;
    private static LocksViewModel locksViewModel;
    private static UsersViewModel usersViewModel;
    private static AccessViewModel accessViewModel;

    public static UsersViewModel getUsersViewModel() {
        return usersViewModel;
    }

    public static AuthViewModel getAuthViewModel() {
        return authViewModel;
    }


    public static LocksViewModel getLocksViewModel() {
        return locksViewModel;
    }

    public static SharedPreferences getSettings() {
        return settings;
    }

    public static AccessViewModel getAccessViewModel() {
        return accessViewModel;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("Connection", "close").build();
                        return chain.proceed(request);
                    }
                })
                .build();

        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String ip = settings.getString(APP_PREFERENCES_IP, "172.18.198.34");

        //создание объекта retrofit
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://" + ip + ":8000/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        // иницализация authViewModel
        authViewModel = new AuthViewModel(this);

        // инициализация locksViewModel
        locksViewModel = new LocksViewModel();

        // инициализация usersViewModel
        usersViewModel = new UsersViewModel();

        // инициализация accessesViesModel
        accessViewModel = new AccessViewModel();
    }

    public enum ApplicationState {
        STOPED,
        STARTED
    }

    public static void setRetrofit(Retrofit retrofit) {
        App.retrofit = retrofit;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
