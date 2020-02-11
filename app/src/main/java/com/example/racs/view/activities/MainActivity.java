package com.example.racs.view.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.racs.R;
import com.example.racs.view.fragments.LocksFragment;
import com.example.racs.view.fragments.MainFragment;
import com.example.racs.view.fragments.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_activity, MainFragment.newInstance())
                .commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.locks:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.host_activity, LocksFragment.newInstance())
                                .commit();
                        break;
                    case R.id.users:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.host_activity, UsersFragment.newInstance())
                                .commit();
                        break;
                    case R.id.main:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.host_activity, MainFragment.newInstance())
                                .commit();
                        break;
                }
                return true;
            }
        });
    }


}