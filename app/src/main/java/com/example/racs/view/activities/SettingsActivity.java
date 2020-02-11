package com.example.racs.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.racs.R;
import com.example.racs.data.api.RetrofitObject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences settings;
    private Spinner ips;
    private static final String APP_PREFERENCES = "mysettings";
    private static final String APP_PREFERENCES_IP = "IP";
    private static final String TEXTVIEW_STATE_KEY = "TEXTVIEW_STATE_KEY";
    private static final String POSITION_KEY = "POSITION_KEY";
    private static String ip;
    private static final String MAIN_ACTIVITY = "MainActivity";
    private static final String AUTH_ACTIVITY = "AuthActivity";
    private static final String NAME = "activity name";


    public static void setIp(String ip) {
        SettingsActivity.ip = ip;
    }

    public static String getIp() {
        return ip;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        ips = findViewById(R.id.spinner);


        if (settings.contains(APP_PREFERENCES_IP)) {
            ips.setSelection(settings.getString(APP_PREFERENCES_IP, ips.getSelectedItem().toString()).equals("172.18.198.34")
                    ? 0 : 1);
        }
        ips.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ipAdress = ips.getSelectedItem().toString();
                setIp(ipAdress);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(APP_PREFERENCES_IP, ipAdress);
                editor.apply();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://" + SettingsActivity.ip + ":8000/api/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitObject.setRetrofit(retrofit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putString(TEXTVIEW_STATE_KEY, ips.getSelectedItem().toString());
        outState.putInt(POSITION_KEY, ips.getSelectedItemPosition());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent nextIntent;
        Intent curIntent = getIntent();
        if (curIntent.getStringExtra(NAME).equals(MAIN_ACTIVITY)) {
            nextIntent = new Intent(SettingsActivity.this, MainActivity.class);
        } else {
            nextIntent = new Intent(SettingsActivity.this, AuthorizationActivity.class);
        }

        startActivity(nextIntent);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    public void onBackClick(View view) {
        Intent nextIntent;
        Intent curIntent = getIntent();
        if (curIntent.getStringExtra(NAME).equals(MAIN_ACTIVITY)) {
            nextIntent = new Intent(SettingsActivity.this, MainActivity.class);
        } else {
            nextIntent = new Intent(SettingsActivity.this, AuthorizationActivity.class);
        }

        startActivity(nextIntent);
        finish();
    }


}
