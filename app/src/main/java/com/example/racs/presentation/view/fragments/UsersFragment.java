package com.example.racs.presentation.view.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.UsersRepository;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.domain.usecases.deleteusecases.DeleteUser;
import com.example.racs.domain.usecases.getusecases.GetUsers;
import com.example.racs.presentation.view.activities.AccessActivity;
import com.example.racs.presentation.view.activities.AddUserActivity;
import com.example.racs.presentation.view.adapters.UserHolder;
import com.example.racs.presentation.view.adapters.UsersAdapter;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class UsersFragment extends Fragment {

    private static final int DEFAULT_NUMBER = 50;
    private static RecyclerView usersRecycler;
    private static UsersAdapter usersAdapter;
    private TextView undlined;
    private static boolean isAdded = false;
    private static List<UsersEntity.User> users;
    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";
    private Button add_btn;
    private static final UsersRepository usersRepository = new UsersRepository();
    private static GetUsers usecaseGetUsers;
    private static DeleteUser usecaseDeleteUser;

    public static UsersFragment newInstance() {

        Bundle args = new Bundle();

        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_users, container, false);
        usersRecycler = root.findViewById(R.id.users_list);
        undlined = root.findViewById(R.id.change_assess);
        add_btn = root.findViewById(R.id.add_user1);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settings = getActivity().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        usersAdapter = new UsersAdapter(new UserHolder.OnBinClickListener() {
            @Override
            public void onBinClick(View v, long id) {
                usecaseDeleteUser = new DeleteUser(usersRepository, settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(Boolean smt) {
                        getUsers(DEFAULT_NUMBER);
                    }
                });
                usecaseDeleteUser.deleteUser();
            }
        });
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        usersRecycler.addItemDecoration(itemDecoration);
        getUsers(DEFAULT_NUMBER);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddUserActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        undlined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccessActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        isAdded = data.getBooleanExtra(AddUserActivity.ADDED_NEW_VALUE, isAdded);
        getUsers(isAdded ? users.size() + 1 : users.size());
    }

    public static void getUsers(int count) {
        usecaseGetUsers = new GetUsers(usersRepository, settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER, new OnCompleteListener<List<UsersEntity.User>>() {
            @Override
            public void onComplete(List<UsersEntity.User> smt) {
                users = smt;
                usersRecycler.setAdapter(usersAdapter);
                usersAdapter.replaceUsers(users);
            }
        });
        usecaseGetUsers.getUsers();

    }

}
