package com.example.racs.Users;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.racs.Access.Access;
import com.example.racs.App;
import com.example.racs.Locks.LocksActivity;
import com.example.racs.MainActivity;
import com.example.racs.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Users extends AppCompatActivity {

    private static final int DEFAULT_NUMBER = 50;
    private static RecyclerView usersRecycler;
    private static UsersAdapter usersAdapter;
    private TextView undlined;
    private static boolean isAdded = false;
    public static UsersPOJO users;
    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);


        usersAdapter = new UsersAdapter(Users.this, R.layout.activity_users, new UserHolder.OnBinClickListener() {
            @Override
            public void onBinClick(View v, long id) {
                App.getUserApi().deleteUser(settings.getString(ACCESS_TOKEN, ""), (int)id).enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                        getUsers(users.getUsers().size());
                    }

                    @Override
                    public void onFailure(Call<Response<Void>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
        usersRecycler = findViewById(R.id.users_list);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        usersRecycler.addItemDecoration(itemDecoration);
        getUsers(DEFAULT_NUMBER);
        undlined = findViewById(R.id.change_assess);


        Button add_btn = findViewById(R.id.add_user1);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Users.this, AddUser.class);
                startActivityForResult(intent, 2);  // ЗДЕСЬ НАДО БУДЕТ ЗАПУСКАТЬ ACIVITY FOR RESULT!!!
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){ return;}
        isAdded = data.getBooleanExtra(AddUser.ADDED_NEW_VALUE, isAdded);
        getUsers(isAdded ? users.getUsers().size()+1 : users.getUsers().size());
    }

    public static void getUsers(int count)
    {
        App.getUserApi().getUsers(settings.getString(ACCESS_TOKEN, ""), count).enqueue(new Callback<UsersPOJO>() {
            @Override
            public void onResponse(Call<UsersPOJO> call, Response<UsersPOJO> response) {
                users = response.body();
                usersRecycler.setAdapter(usersAdapter);
                usersAdapter.replaceUsers(users.getUsers());
            }

            @Override
            public void onFailure(Call<UsersPOJO> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void onChangeClick (View view) {
        Intent intent = new Intent(Users.this, Access.class);
        startActivity(intent);
    }


    //Аналогичные методы, как в MainActivity.java
    public void onLockClick(View view) {
        Intent lockIntent = new Intent(this, LocksActivity.class);
        startActivity(lockIntent);
        finish();
    }

    public void onHomeClick(View view){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}
