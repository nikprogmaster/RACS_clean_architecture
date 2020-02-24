package com.example.racs.presentation.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.racs.App;
import com.example.racs.R;
import com.example.racs.model.data.AuthEntityData;
import com.example.racs.model.data.AuthPostEntityData;
import com.example.racs.presentation.viewmodel.AuthViewModel;

public class AuthorizationActivity extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "AuthActivity";
    private static final String NAME = "activity name";

    private EditText username;
    private EditText password;
    private Button enterButton;
    private ImageView settingsButton;
    private AuthViewModel authViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        bindViews();

        authViewModel = ViewModelProviders.of(this, App.getAuthModelFactory())
                .get(AuthViewModel.class);

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(AuthorizationActivity.this, SettingsActivity.class);
            intent.putExtra(NAME, ACTIVITY_NAME);
            startActivity(intent);
            finish();
        });

        enterButton.setOnClickListener(v -> onEnterButtonClick());
    }

    private void onEnterButtonClick() {
        if (!username.getText().toString().equals("") && !password.getText().toString().equals("")) {

            authViewModel.onReceive(username.getText().toString(), password.getText().toString());

            // подписываемся на сохранение логина, пароля
            observeOnAuthData();

            // подписываемся на изменение access-токена
            observeOnAccessToken();

            observeOnFirstStarted();

        } else {
            username.setError(getResources().getString(R.string.error_message));
        }
    }

    private void observeOnAuthData() {
        LiveData<AuthPostEntityData> loginPassData = authViewModel.getLoginPassData();
        loginPassData.observe(AuthorizationActivity.this, authPostEntityData -> {
            username.setText(authPostEntityData.getEmail());
            password.setText(authPostEntityData.getPassword());
        });
    }

    private void observeOnAccessToken() {
        LiveData<AuthEntityData> data = authViewModel.getData();
        data.observe(AuthorizationActivity.this, authEntityData ->
                Toast.makeText(AuthorizationActivity.this, "Access token changed", Toast.LENGTH_SHORT).show());
    }

    private void observeOnFirstStarted() {
        LiveData<Boolean> firstStarted = authViewModel.getFirstStarted();
        firstStarted.observe(AuthorizationActivity.this, aBoolean -> {
            if (aBoolean) {
                Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void bindViews() {
        username = findViewById(R.id.username_et);
        password = findViewById(R.id.password_et);
        enterButton = findViewById(R.id.enter_button);
        settingsButton = findViewById(R.id.settings_auth);
    }
}
