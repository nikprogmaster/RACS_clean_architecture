package com.example.racs.presentation.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;
import com.example.racs.data.api.App;
import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.presentation.view.activities.AddAccessActivity;
import com.example.racs.presentation.view.activities.OnBackClickListener;
import com.example.racs.presentation.view.adapters.AccessAdapter;
import com.example.racs.presentation.viewmodel.AccessViewModel;
import com.example.racs.presentation.viewmodel.LocksViewModel;
import com.example.racs.presentation.viewmodel.UsersViewModel;

import java.util.List;

public class AccessFragment extends Fragment {

    private static final int DEFAULT_NUMBER = 100;
    private static RecyclerView accessRecycler;
    private static AccessAdapter accessAdapter;
    private static List<AccessEntity.AccPOJO> accesses;
    private static boolean isAdded = false;
    private Button add_btn;
    private ImageView back;
    private OnBackClickListener onBackClickListener;
    private static List<LocksEntity.Lock> lockList;
    private static List<UsersEntity.User> usersList;
    private OnCompleteListener<Boolean> onCompleteListener;
    private AccessAdapter.OnDeleteListener onDeleteListener = new AccessAdapter.OnDeleteListener() {
        @Override
        public void onDelete(int position) {
            AccessViewModel accessViewModel = App.getAccessViewModel();
            accessViewModel.deleteAccess(position);
        }
    };

    public static AccessFragment newInstance() {

        Bundle args = new Bundle();

        AccessFragment fragment = new AccessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_access, container, false);
        add_btn = root.findViewById(R.id.button2);
        back = root.findViewById(R.id.back);
        accessRecycler = root.findViewById(R.id.access_list);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        accessRecycler.addItemDecoration(itemDecoration);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onBackClickListener = (OnBackClickListener) getActivity();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClickListener.onBackClick();
            }
        });

        onCompleteListener = new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(Boolean smt) {
                accessAdapter = new AccessAdapter(lockList, usersList, onDeleteListener);
                accessRecycler.setAdapter(accessAdapter);
                accessAdapter.replaceAccesses(accesses);
            }
        };

        observeLocks();
        observeUsers();
        observeAccesses();
        accessAdapter = new AccessAdapter(lockList, usersList, onDeleteListener);
        accessRecycler.setAdapter(accessAdapter);
        accessAdapter.replaceAccesses(accesses);

        updateLocks();
        updateUsers();
        updateAccesses();



        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddAccessActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        isAdded = data.getBooleanExtra(AddAccessActivity.ADDED, isAdded);
        observeAccesses();

    }

    private void observeAccesses() {
        AccessViewModel accessViewModel = App.getAccessViewModel();
        LiveData<List<AccessEntity.AccPOJO>> data = accessViewModel.getData();
        accesses = data.getValue();
        data.observe(this, new Observer<List<AccessEntity.AccPOJO>>() {
            @Override
            public void onChanged(List<AccessEntity.AccPOJO> accPOJOS) {
                accesses = accPOJOS;
            }
        });
    }

    private void observeUsers(){
        UsersViewModel usersViewModel = App.getUsersViewModel();
        LiveData<List<UsersEntity.User>> usersData = usersViewModel.getData(DEFAULT_NUMBER);
        usersList = usersData.getValue();
        usersData.observe(this, new Observer<List<UsersEntity.User>>() {
            @Override
            public void onChanged(List<UsersEntity.User> users) {
                usersList = users;
            }
        });
    }

    private void observeLocks(){
        LocksViewModel locksViewModel = App.getLocksViewModel();
        LiveData<List<LocksEntity.Lock>> locksData = locksViewModel.getData();
        lockList = locksData.getValue();
        locksData.observe(this, new Observer<List<LocksEntity.Lock>>() {
            @Override
            public void onChanged(List<LocksEntity.Lock> locks) {
                lockList = locks;
            }
        });
    }

    private void updateAccesses(){
        AccessViewModel accessViewModel = App.getAccessViewModel();
        accessViewModel.loadData();
        onCompleteListener.onComplete(true);
    }

    private void updateUsers() {
        UsersViewModel usersViewModel = App.getUsersViewModel();
        usersViewModel.loadData(DEFAULT_NUMBER);
    }

    private void updateLocks() {
        LocksViewModel locksViewModel = App.getLocksViewModel();
        locksViewModel.loadData();
    }

}
