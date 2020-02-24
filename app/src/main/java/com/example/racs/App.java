package com.example.racs;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.racs.data.api.AccessApi;
import com.example.racs.data.api.AuthorizationApi;
import com.example.racs.data.api.LockApi;
import com.example.racs.data.api.UserApi;
import com.example.racs.data.api.UserImpl;
import com.example.racs.data.repository.AccessesRepository;
import com.example.racs.data.repository.AuthRepository;
import com.example.racs.data.repository.LocksRepository;
import com.example.racs.data.repository.UsersRepository;
import com.example.racs.presentation.viewmodel.factories.AccessModelFactory;
import com.example.racs.presentation.viewmodel.factories.AuthModelFactory;
import com.example.racs.presentation.viewmodel.factories.LocksModelFactory;
import com.example.racs.presentation.viewmodel.factories.UsersModelFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static final String APP_PREFERENCES = "mysettings";
    private static final String APP_PREFERENCES_IP = "IP";

    public static retrofit2.Retrofit retrofit;
    private static SharedPreferences settings;
    private static LockApi lockApi;
    private static AuthorizationApi authApi;
    private static UserApi userApi;
    private static AccessApi accessApi;
    private static AuthModelFactory authModelFactory;
    private static LocksModelFactory locksModelFactory;
    private static UsersModelFactory usersModelFactory;
    private static AccessModelFactory accessModelFactory;
    private static UserImpl userImpl;

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        lockApi = retrofit.create(LockApi.class);
        authApi = retrofit.create(AuthorizationApi.class);
        userApi = retrofit.create(UserApi.class);
        accessApi = retrofit.create(AccessApi.class);

        userImpl = new UserImpl(userApi);


    }


    public static LocksModelFactory getLocksModelFactory() {
        if (locksModelFactory == null) {
            locksModelFactory = new LocksModelFactory(new LocksRepository(lockApi));
        }
        return locksModelFactory;
    }


    public static AuthModelFactory getAuthModelFactory() {
        if (authModelFactory == null) {
            authModelFactory = new AuthModelFactory(new AuthRepository(authApi));
        }
        return authModelFactory;
    }


    public static UsersModelFactory getUsersModelFactory() {
        if (usersModelFactory == null) {
            usersModelFactory = new UsersModelFactory(new UsersRepository(userApi, userImpl));
        }
        return usersModelFactory;
    }


    public static AccessModelFactory getAccessModelFactory() {
        if (accessModelFactory == null) {
            accessModelFactory = new AccessModelFactory(new AccessesRepository(accessApi));
        }
        return accessModelFactory;
    }


    public static SharedPreferences getSettings() {
        return settings;
    }

    public static void setRetrofit(Retrofit retrofit) {
        App.retrofit = retrofit;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
