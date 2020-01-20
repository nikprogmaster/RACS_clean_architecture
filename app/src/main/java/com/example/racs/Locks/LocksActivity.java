package com.example.racs.Locks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.App;
import com.example.racs.MainActivity;
import com.example.racs.R;
import com.example.racs.Users.Users;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocksActivity extends AppCompatActivity {

    private static RecyclerView locks_recycler;
    TextView tw;
    private static final int DEFAULT_NUMBER = 50;
    private static LocksAdapter locks_adapter;
    public static LocksPOJO locks;
    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locks);
        tw = findViewById(R.id.textView3);

        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        LocksAdapter.OnLockClickListener onLockClickListener = new LocksAdapter.OnLockClickListener() {
            @Override
            public void onLockClick(LocksPOJO.Lock lock) {
                Intent intent = new Intent(LocksActivity.this, LockActivity.class);
                intent.putExtra(LockActivity.EXTRA_LOCKID, lock.getLId());
                startActivity(intent);
            }
        };
        locks_adapter = new LocksAdapter(onLockClickListener, false);
        locks_recycler = findViewById(R.id.locks_recycler);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        locks_recycler.addItemDecoration(itemDecoration);
        getLocks(DEFAULT_NUMBER);
    }

    public void onBinClick(View view) {
        View parent = (View) view.getParent();
        TextView t = (TextView) parent.findViewById(R.id.tw_id);
        String s = String.valueOf(t.getText());
        int id = Integer.valueOf(s);
        App.getApi().deleteLock(settings.getString(ACCESS_TOKEN, ""), id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(LocksActivity.this, "Замок удален", Toast.LENGTH_SHORT).show();
                getLocks(locks.getResults().size());
                locks_adapter.replaceLocks(locks.getResults());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public static void getLocks(int count) {
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



    //аналогичные методы, что и в MainActivity.java
    public void onFaceClick(View view) {
        Intent userIntent = new Intent(this, Users.class);
        startActivity(userIntent);
        finish();
    }

    public void onHomeClick(View view){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
