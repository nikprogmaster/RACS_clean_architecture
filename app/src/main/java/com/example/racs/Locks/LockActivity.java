package com.example.racs.Locks;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.Access.AccessPOJO;
import com.example.racs.MainActivity;
import com.example.racs.R;
import com.example.racs.Users.UsersPOJO;

public class LockActivity extends AppCompatActivity {

    public static final String EXTRA_LOCKID = "drinkId";
    private UsersofLockAdapter usersofLockAdapter;
    private RecyclerView userRecyclerView;
    private TextView lockName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        int lockId = (Integer) getIntent().getExtras().get(EXTRA_LOCKID);

        AccessPOJO accesses = new AccessPOJO();
        UsersPOJO users = accesses.searchUsersByLock(lockId, MainActivity.accesses.getResults(), MainActivity.users.getUsers());
        Log.i("Сколько юзеров", String.valueOf(users.getUsers().size()));
        lockName = findViewById(R.id.lock_tw);
        lockName.setText("Замок " + lockId);
        userRecyclerView = findViewById(R.id.users_of_lock_recycler);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        userRecyclerView.addItemDecoration(itemDecoration);
        usersofLockAdapter = new UsersofLockAdapter();
        userRecyclerView.setAdapter(usersofLockAdapter);
        usersofLockAdapter.replaceUsers(users.getUsers());
    }

    public void onBack2Click(View view) {
        finish();
    }
}
