package com.example.racs;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Spinner;

import com.example.racs.Access.AccessApi;
import com.example.racs.Authorization.AuthorizationApi;
import com.example.racs.Locks.LockApi;
import com.example.racs.Users.UserApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class App extends Application {

    private static LockApi lockApi, lockApi2;
    private static UserApi userApi;
    private static AccessApi accessApi;
    private static AuthorizationApi authApi;
    public static retrofit2.Retrofit retrofit2, retrofit;
    private Spinner IPs;
    private static final String APP_PREFERENCES = "mysettings";
    private static final String APP_PREFERENCES_IP = "IP";

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
        //создание класса на основе интерфейса
        lockApi = retrofit.create(LockApi.class);
        userApi = retrofit.create(UserApi.class);
        accessApi = retrofit.create(AccessApi.class);
        //authApi = retrofit.create(AuthorizationApi.class);


        retrofit2 = new retrofit2.Retrofit.Builder()
                .baseUrl("http://172.18.198.34:8000/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        //создание класса на основе интерфейса
        authApi = retrofit2.create(AuthorizationApi.class);
    }
    public retrofit2.Retrofit retrofitCreator() {
       /* final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();*/

        return new retrofit2.Retrofit.Builder()
                .baseUrl("http://172.18.198.31:8000/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //обыкновенные геттеры для private переменных
    public static LockApi getApi() {
        return lockApi;
    }
    public static LockApi getApi2() {
        return lockApi2;
    }
    public static UserApi getUserApi() { return userApi;}
    public static AccessApi getAccessApi() { return  accessApi; }
    public static AuthorizationApi getAuthApi() { return authApi; }

    public static void setLockApi(LockApi lockApi) { App.lockApi = lockApi; }
    public static void setLockApi2(LockApi lockApi2) { App.lockApi2 = lockApi2; }
    public static void setUserApi(UserApi userApi) { App.userApi = userApi; }
    public static void setAccessApi(AccessApi accessApi) { App.accessApi = accessApi; }
    public static void setAuthApi(AuthorizationApi authApi) { App.authApi = authApi; }
}

