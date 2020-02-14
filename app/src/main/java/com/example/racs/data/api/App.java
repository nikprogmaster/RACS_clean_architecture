package com.example.racs.data.api;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    public static retrofit2.Retrofit retrofit;
    private static final String APP_PREFERENCES = "mysettings";
    private static final String APP_PREFERENCES_IP = "IP";

    private static MutableLiveData<ApplicationState> state = new MutableLiveData<>(ApplicationState.STARTED);

    public static MutableLiveData<ApplicationState> getState() {
        return state;
    }

    public static void setState(MutableLiveData<ApplicationState> state) {
        App.state = state;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        SharedPreferences settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        String ip = settings.getString(APP_PREFERENCES_IP, "172.18.198.34");

        //создание объекта retrofit
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://" + ip + ":8000/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

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
