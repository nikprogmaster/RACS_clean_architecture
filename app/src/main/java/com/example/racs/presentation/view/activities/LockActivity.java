package com.example.racs.presentation.view.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;
import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.AccessesRepository;
import com.example.racs.data.repository.UsersRepository;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.domain.usecases.getusecases.GetAccesses;
import com.example.racs.domain.usecases.getusecases.GetAccessesToLock;
import com.example.racs.domain.usecases.getusecases.GetUsers;
import com.example.racs.presentation.view.adapters.UsersofLockAdapter;

import java.util.List;

public class LockActivity extends AppCompatActivity {

    public static final String EXTRA_LOCKID = "drinkId";
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";
    private static final int DEFAULT_NUMBER = 50;
    private static SharedPreferences settings;
    private UsersofLockAdapter usersofLockAdapter;
    private RecyclerView userRecyclerView;
    private TextView lockName;
    private List<UsersEntity.User> allusers;
    private List<AccessEntity.AccPOJO> accesses;
    private static final AccessesRepository accessesRepository = new AccessesRepository();
    private static final UsersRepository usersRepository = new UsersRepository();
    private static GetAccesses usecaseGetAccesses;
    private static GetUsers usecaseGetUsers;
    private static GetAccessesToLock usecaseGetAccessesToLock;
    private OnCompleteListener<List<AccessEntity.AccPOJO>> onCompleteListener;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        final int lockId = (Integer) getIntent().getExtras().get(EXTRA_LOCKID);
        initViews(lockId);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        usecaseGetUsers = new GetUsers(usersRepository, settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER, new OnCompleteListener<List<UsersEntity.User>>() {
            @Override
            public void onComplete(List<UsersEntity.User> smt) {
                allusers = smt;
            }
        });
        usecaseGetUsers.getUsers();

        usecaseGetAccesses = new GetAccesses(accessesRepository, settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER, new OnCompleteListener<List<AccessEntity.AccPOJO>>() {
            @Override
            public void onComplete(List<AccessEntity.AccPOJO> smt) {
                accesses = smt;
                onCompleteListener.onComplete(accesses);
            }
        });
        usecaseGetAccesses.getAccesses();

        onCompleteListener = new OnCompleteListener<List<AccessEntity.AccPOJO>>() {
            @Override
            public void onComplete(List<AccessEntity.AccPOJO> smt) {
                usecaseGetAccessesToLock = new GetAccessesToLock(accessesRepository, lockId, accesses, allusers, new OnCompleteListener<List<UsersEntity.User>>() {
                    @Override
                    public void onComplete(List<UsersEntity.User> smt) {
                        usecaseGetAccessesToLock.getAccessesToLock();
                        usersofLockAdapter.replaceUsers(smt);
                    }
                });
                usecaseGetAccessesToLock.getAccessesToLock();
            }
        };

    }

    private void initViews(int lockId) {
        lockName = findViewById(R.id.lock_tw);
        lockName.setText(getResources().getString(R.string.lock) + lockId);
        back = findViewById(R.id.back2);
        userRecyclerView = findViewById(R.id.users_of_lock_recycler);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        userRecyclerView.addItemDecoration(itemDecoration);
        usersofLockAdapter = new UsersofLockAdapter();
        userRecyclerView.setAdapter(usersofLockAdapter);
    }

}
