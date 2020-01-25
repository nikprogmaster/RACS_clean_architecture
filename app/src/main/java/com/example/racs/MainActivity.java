package com.example.racs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.Access.AccessPOJO;
import com.example.racs.Authorization.AuthorizationActivity;
import com.example.racs.Locks.LockActivity;
import com.example.racs.Locks.LocksActivity;
import com.example.racs.Locks.LocksAdapter;
import com.example.racs.Locks.LocksPOJO;
import com.example.racs.Users.Users;
import com.example.racs.Users.UsersPOJO;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static LocksPOJO locks;
    public static UsersPOJO users;
    public static AccessPOJO accesses;
    private static RecyclerView locks_recycler;
    private static LocksAdapter locks_adapter;
    private static final int DEFAULT_NUMBER = 50;
    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";
    private static final String ACTIVITY_NAME = "MainActivity";
    private static final String NAME = "activity name";


    Button log_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locks_recycler = findViewById(R.id.lw);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        locks_recycler.addItemDecoration(itemDecoration);
        //Получаем дату от сервера
        //getDate();

        //Получаем настройки
        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        //получаем список пользователей от сервера
        getUsers(DEFAULT_NUMBER);

        //получаем список доступов
        getAccesses(DEFAULT_NUMBER);

        LocksAdapter.OnLockClickListener onLockClickListener = new LocksAdapter.OnLockClickListener() {
            @Override
            public void onLockClick(LocksPOJO.Lock lock) {
                Intent intent = new Intent(MainActivity.this, LockActivity.class);
                intent.putExtra(LockActivity.EXTRA_LOCKID, lock.getLId());
                startActivity(intent);
            }
        };
        locks_adapter = new LocksAdapter(onLockClickListener, true);

        //Получаем список замков от сервера
        getLocks(DEFAULT_NUMBER);
    }



    public void getDate(){
        Call<String> stringCall = App.getApi2().getDate(settings.getString(ACCESS_TOKEN, ""), "http://172.18.198.34:8000/datestart");
        stringCall.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                String responseString = response.body();
                ServerDate sd = new ServerDate(responseString);
                sd.parseDate();
                TextView t = findViewById(R.id.date);
                t.setText("Время выполнения: " + sd.getDiff());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getUsers(int count)
    {
        App.getUserApi().getUsers(settings.getString(ACCESS_TOKEN, ""), count).enqueue(new Callback<UsersPOJO>() {
            @Override
            public void onResponse(Call<UsersPOJO> call, Response<UsersPOJO> response) {
                users = response.body();
                /*if (users == null && response.code() == 403){
                    authorizationActivityStart();
                }*/
            }

            @Override
            public void onFailure(Call<UsersPOJO> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void getLocks(int count) {
        Log.i("MainActivity", settings.getString(ACCESS_TOKEN, ""));
        App.getApi().getLocks(settings.getString(ACCESS_TOKEN, ""), count).enqueue(new Callback<LocksPOJO>() {
            @Override
            public void onResponse(Call<LocksPOJO> call, retrofit2.Response<LocksPOJO> response) {
                if (response.isSuccessful()) {
                    // добавляем все элементы в список
                    locks = response.body();
                    locks_recycler.setAdapter(locks_adapter);
                    locks_adapter.replaceLocks(locks.getResults());
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                }
            }

            @Override
            public void onFailure(Call<LocksPOJO> call, Throwable t) {
                if (t instanceof IOException) {
                    t.printStackTrace();
                    //Toast.makeText(context, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                } else {
                    //Toast.makeText(context, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }
            }
        });
    }


    public static void getAccesses(int count) {
        App.getAccessApi().getAccesses(settings.getString(ACCESS_TOKEN, ""), count).enqueue(new Callback<AccessPOJO>() {
            @Override
            public void onResponse(Call<AccessPOJO> call, Response<AccessPOJO> response) {
                accesses = response.body();
            }

            @Override
            public void onFailure(Call<AccessPOJO> call, Throwable t) {

            }
        });
    }

    //Метод, вызывающийся при нажатии на значок "Settings"
    public void onSettingsClick (View view) {
        Intent intent = new Intent(MainActivity.this, Settings.class);
        intent.putExtra(NAME, ACTIVITY_NAME);
        startActivity(intent);
        finish();
    }

    //Метод, вызывающийся при нажатии на значок "замок"
    public void onLockClick (View view){
        Intent lockIntent = new Intent(MainActivity.this, LocksActivity.class);
        startActivity(lockIntent);
        finish();
    }

    //метод, вызывающийся при нажатии на кнопку "пользователь"
    public void onFaceClick (View view){
        Intent userIntent = new Intent(MainActivity.this, Users.class);
        startActivity(userIntent);
        finish();
    }

    private void authorizationActivityStart (){
        Intent intent = new Intent(MainActivity.this, AuthorizationActivity.class);
        startActivity(intent);
        finish();
    }
}