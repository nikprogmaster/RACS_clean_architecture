package com.example.racs.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.racs.R;
import com.example.racs.data.entities.AuthEntity;
import com.example.racs.data.entities.AuthPostEntity;
import com.example.racs.data.repository.AuthRepository;
import com.example.racs.domain.usecases.Authorize;
import com.example.racs.domain.usecases.OnCompleteListener;

public class AuthorizationActivity extends AppCompatActivity {

    private static final String REFRESH_TOKEN = "REFRESH";
    private static final String LOGIN_KEY = "LOGIN";
    private static final String PASSWORD_KEY = "PASSWORD";
    private EditText username, password;
    private Button enter_btn;
    private static String access = "Bearer ";
    private SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";
    private static final String ACTIVITY_NAME = "AuthActivity";
    private static final String NAME = "activity name";
    private static Authorize authorize;
    private AuthEntity authEntity;

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
        bindViews();

        if (savedInstanceState != null) {
            username.setText(savedInstanceState.getString(LOGIN_KEY));
            password.setText(savedInstanceState.getString(PASSWORD_KEY));
        }
        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);


        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    authorize = new Authorize(new AuthRepository(), new AuthPostEntity(username.getText().toString(), password.getText().toString()), new OnCompleteListener<AuthEntity>() {
                        @Override
                        public void onComplete(AuthEntity authEntity) {
                            access += authEntity.getAccess();
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString(ACCESS_TOKEN, access);
                            editor.putString(REFRESH_TOKEN, authEntity.getRefresh());
                            editor.apply();
                            Log.i("Авторизация", settings.getString(ACCESS_TOKEN, ""));
                            Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    authEntity = authorize.authorize();

                } else {
                    username.setError(getResources().getString(R.string.error_message));
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
        bundle.putString(LOGIN_KEY, username.getText().toString());
        bundle.putString(PASSWORD_KEY, password.getText().toString());
    }

    public void onSettingsClick(View view) {
        Intent intent = new Intent(AuthorizationActivity.this, SettingsActivity.class);
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
