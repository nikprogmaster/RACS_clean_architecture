package com.example.racs.presentation.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.racs.R;
import com.example.racs.data.api.App;
import com.example.racs.data.entities.AuthEntity;
import com.example.racs.data.entities.AuthPostEntity;
import com.example.racs.presentation.viewmodel.AuthViewModel;

public class AuthorizationActivity extends AppCompatActivity {

    private EditText username, password;
    private Button enter_btn;
    private ImageView settingsButton;
    private static final String ACTIVITY_NAME = "AuthActivity";
    private static final String NAME = "activity name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        bindViews();

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AuthorizationActivity.this, SettingsActivity.class);
                intent.putExtra(NAME, ACTIVITY_NAME);
                startActivity(intent);
                finish();
            }
        });

        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username.getText().toString().equals("") && !password.getText().toString().equals("")) {
                    AuthViewModel authViewModel = App.getAuthViewModel();
                    authViewModel.onReceive(username.getText().toString(), password.getText().toString());

                    // подписываемся на сохранение логина, пароля
                    LiveData<AuthPostEntity> loginPassData = authViewModel.getLoginPassData();
                    loginPassData.observe(AuthorizationActivity.this, new Observer<AuthPostEntity>() {
                        @Override
                        public void onChanged(AuthPostEntity authPostEntity) {
                            username.setText(authPostEntity.getEmail());
                            password.setText(authPostEntity.getPassword());
                        }
                    });

                    // подписываемся на изменение access-токена
                    LiveData<AuthEntity> data = authViewModel.getData();
                    data.observe(AuthorizationActivity.this, new Observer<AuthEntity>() {
                        @Override
                        public void onChanged(AuthEntity authEntity) {
                            Toast.makeText(AuthorizationActivity.this, "Access token changed", Toast.LENGTH_SHORT).show();
                        }
                    });


                    LiveData<Boolean> firstStarted = authViewModel.getFirstStarted();
                    firstStarted.observe(AuthorizationActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean){
                                Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });


                } else {
                    username.setError(getResources().getString(R.string.error_message));
                }
            }
        });
    }



    private void bindViews() {
        username = findViewById(R.id.username_et);
        password = findViewById(R.id.password_et);
        enter_btn = findViewById(R.id.enter_button);
        settingsButton = findViewById(R.id.settings_auth);
    }
}
