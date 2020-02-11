package com.example.racs.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;
import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.AccessesRepository;
import com.example.racs.data.repository.LocksRepository;
import com.example.racs.data.repository.UsersRepository;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.domain.usecases.deleteusecases.DeleteAccess;
import com.example.racs.domain.usecases.getusecases.GetAccesses;
import com.example.racs.domain.usecases.getusecases.GetLocks;
import com.example.racs.domain.usecases.getusecases.GetUsers;
import com.example.racs.view.adapters.AccessAdapter;

import java.util.List;

public class AccessActivity extends AppCompatActivity {

    private static final int DEFAULT_NUMBER = 50;
    private static RecyclerView accessRecycler;
    private static AccessAdapter accessAdapter;
    private static List<AccessEntity.AccPOJO> accesses;
    private static boolean isAdded = false;
    private Button add_btn;
    private ImageView back;
    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";
    private static final AccessesRepository accessesRepository = new AccessesRepository();
    private static GetAccesses usecaseGetAccesses;
    private static DeleteAccess usecaseDeleteAccess;
    private static List<LocksEntity.Lock> locks;
    private static List<UsersEntity.User> users;
    private static final LocksRepository locksRepository = new LocksRepository();
    private static final UsersRepository usersRepository = new UsersRepository();
    private GetLocks usecaseGetLocks;
    private GetUsers usecaseGetUsers;
    private OnCompleteListener<Boolean> onCompleteListener;
    private AccessAdapter.OnDeleteListener onDeleteListener = new AccessAdapter.OnDeleteListener() {
        @Override
        public void onDelete(int position) {
            usecaseDeleteAccess = new DeleteAccess(accessesRepository, settings.getString(ACCESS_TOKEN, ""), position, new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(Boolean smt) {
                    getAccesses(DEFAULT_NUMBER);
                }
            });
            usecaseDeleteAccess.deleteUser();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);
        add_btn = findViewById(R.id.button2);
        back = findViewById(R.id.back);


        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        onCompleteListener = new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(Boolean smt) {
                accessAdapter = new AccessAdapter(locks, users, onDeleteListener);
                getAccesses(DEFAULT_NUMBER);
            }
        };

        getUsers(DEFAULT_NUMBER);
        getLocks(DEFAULT_NUMBER);
        accessRecycler = findViewById(R.id.access_list);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        accessRecycler.addItemDecoration(itemDecoration);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccessActivity.this, AddAccessActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        isAdded = data.getBooleanExtra(AddAccessActivity.ADDED, isAdded);
        getAccesses(isAdded ? accesses.size() + 1 : accesses.size());

    }

    public static void getAccesses(int count) {
        usecaseGetAccesses = new GetAccesses(accessesRepository, settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER, new OnCompleteListener<List<AccessEntity.AccPOJO>>() {
            @Override
            public void onComplete(List<AccessEntity.AccPOJO> smt) {
                accesses = smt;
                accessRecycler.setAdapter(accessAdapter);
                accessAdapter.replaceAccesses(accesses);
            }
        });
        usecaseGetAccesses.getAccesses();

    }

    public void getUsers(int count) {
        usecaseGetUsers = new GetUsers(usersRepository, settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER, new OnCompleteListener<List<UsersEntity.User>>() {
            @Override
            public void onComplete(List<UsersEntity.User> smt) {
                users = smt;

            }
        });
        usecaseGetUsers.getUsers();
    }

    public void getLocks(int count) {
        usecaseGetLocks = new GetLocks(locksRepository, settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER, new OnCompleteListener<List<LocksEntity.Lock>>() {
            @Override
            public void onComplete(List<LocksEntity.Lock> smt) {
                locks = smt;
                onCompleteListener.onComplete(true);
            }
        });
        usecaseGetLocks.getLocks();
    }

    private void initForAdapter() {


    }


    public void onBackClick2(View view) {
        finish();
    }


}
