package com.example.racs.presentation.view.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.racs.R;
import com.example.racs.presentation.view.fragments.AccessFragment;
import com.example.racs.presentation.view.fragments.AddAccessFragment;
import com.example.racs.presentation.view.fragments.AddUserFragment;
import com.example.racs.presentation.view.fragments.LockUsersFragment;
import com.example.racs.presentation.view.fragments.LocksFragment;
import com.example.racs.presentation.view.fragments.MainFragment;
import com.example.racs.presentation.view.fragments.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnOpenFragmentManager, OnCloseFragmentManager {

    private static final String ACCESS_FRAGMENT_NAME = "ACCESS_FRAGMENT";
    private static final String ADD_ACCESS_FRAGMENT_NAME = "ADD_ACCESS_FRAGMENT";

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_activity, MainFragment.newInstance())
                .addToBackStack(null)
                .commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.locks:
                    clearFragmentBackStack();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.host_activity, LocksFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    break;
                case R.id.users:
                    clearFragmentBackStack();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.host_activity, UsersFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    break;
                case R.id.main:
                    clearFragmentBackStack();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.host_activity, MainFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                    break;
            }
            return true;
        });
    }

    @Override
    public void onAccessFragmentOpen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_activity, AccessFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddAccessFragmentOpen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_activity, AddAccessFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddUserFragmentOpen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_activity, AddUserFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLockUsersFragmentOpen(int lockId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_activity, LockUsersFragment.newInstance(lockId))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClose() {
        getSupportFragmentManager()
                .popBackStack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }

    private void clearFragmentBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

}