package com.example.racs.presentation.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.App;
import com.example.racs.R;
import com.example.racs.model.data.UsersEntityData;
import com.example.racs.presentation.view.activities.OnCloseFragmentManager;
import com.example.racs.presentation.view.adapters.UsersofLockAdapter;
import com.example.racs.presentation.viewmodel.AccessViewModel;
import com.example.racs.presentation.viewmodel.UsersViewModel;

import java.util.List;


public class LockUsersFragment extends Fragment {

    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String LOCK_ID = "LOCK_ID";
    private static final String LOCK = "Замок";

    private UsersofLockAdapter usersofLockAdapter;
    private RecyclerView userRecyclerView;
    private TextView lockName;
    private ImageView back;
    private UsersViewModel usersViewModel;
    private AccessViewModel accessViewModel;
    private LiveData<List<UsersEntityData.User>> usersLiveData;
    private OnCloseFragmentManager onCloseFragmentManager;


    public static LockUsersFragment newInstance(int lockid) {

        Bundle args = new Bundle();
        args.putInt(LOCK_ID, lockid);
        LockUsersFragment fragment = new LockUsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_lock, container, false);
        initViews(root, getArguments().getInt(LOCK_ID));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onCloseFragmentManager = (OnCloseFragmentManager) getActivity();

        back.setOnClickListener(v -> onCloseFragmentManager.onClose());

        usersViewModel = ViewModelProviders.of(getActivity(), App.getUsersModelFactory())
                .get(UsersViewModel.class);
        accessViewModel = ViewModelProviders.of(getActivity(), App.getAccessModelFactory())
                .get(AccessViewModel.class);

        usersLiveData = usersViewModel.getData();
        usersLiveData.observe(this, users -> {
        });

        LiveData<List<UsersEntityData.User>> listLiveData = accessViewModel.getUsersOfLockData();
        listLiveData.observe(this, users -> usersofLockAdapter.replaceUsers(users));
        accessViewModel.getAccessToLock(getArguments().getInt(LOCK_ID), usersLiveData.getValue());
    }

    private void initViews(View root, int lockId) {
        lockName = root.findViewById(R.id.lock_tw);
        lockName.setText(LOCK + lockId);
        back = root.findViewById(R.id.back2);
        userRecyclerView = root.findViewById(R.id.users_of_lock_recycler);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        userRecyclerView.addItemDecoration(itemDecoration);
        usersofLockAdapter = new UsersofLockAdapter();
        userRecyclerView.setAdapter(usersofLockAdapter);
    }
}
