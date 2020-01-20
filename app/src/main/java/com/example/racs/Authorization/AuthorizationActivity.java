package com.example.racs.Authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.racs.App;
import com.example.racs.MainActivity;
import com.example.racs.R;
import com.example.racs.Settings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationActivity extends AppCompatActivity {

    private static final String REFRESH_TOKEN = "REFRESH";
    private EditText username, password;
    private Button enter_btn;
    private static String access = "Bearer ";
    private SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";
    private static final String ACTIVITY_NAME = "AuthActivity";
    private static final String NAME = "activity name";

    public static String getAccess() {
        return access;
    }

    public static void setAccess(String access) {
        AuthorizationActivity.access = access;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        bindViews();

        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username.getText().toString().equals("") && !password.getText().toString().equals("")){
                    App.getAuthApi().authentication(new AuthPostPOJO(username.getText().toString(), password.getText().toString())).enqueue(new Callback<AuthPOJO>() {
                        @Override
                        public void onResponse(Call<AuthPOJO> call, Response<AuthPOJO> response) {
                            AuthPOJO authPOJO = response.body();
                            if (authPOJO!=null){
                                access += authPOJO.getAccess();
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString(ACCESS_TOKEN, access);
                                editor.putString(REFRESH_TOKEN, authPOJO.getRefresh());
                                editor.apply();
                                Log.i("Авторизация", settings.getString(ACCESS_TOKEN, ""));
                                Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else
                                Log.i("auth", "auth is not null");
                        }

                        @Override
                        public void onFailure(Call<AuthPOJO> call, Throwable throwable) {

                        }
                    });
                }
            }
        });
    }

    public void onSettingsClick (View view) {
        Intent intent = new Intent(AuthorizationActivity.this, Settings.class);
        intent.putExtra(NAME, ACTIVITY_NAME);
        startActivity(intent);
        finish();
    }

    private void bindViews() {
        username = findViewById(R.id.username_et);
        password = findViewById(R.id.password_et);
        enter_btn = findViewById(R.id.enter_button);
    }
}
