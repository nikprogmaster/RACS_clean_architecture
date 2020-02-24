package com.example.racs.presentation.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.example.racs.model.data.LocksEntityData;
import com.example.racs.presentation.view.activities.AuthorizationActivity;
import com.example.racs.presentation.view.activities.OnOpenFragmentManager;
import com.example.racs.presentation.view.activities.SettingsActivity;
import com.example.racs.presentation.view.adapters.LocksAdapter;
import com.example.racs.presentation.viewmodel.AccessViewModel;
import com.example.racs.presentation.viewmodel.LocksViewModel;
import com.example.racs.presentation.viewmodel.UsersViewModel;

import java.util.List;

public class MainFragment extends Fragment {

    private static final String ACTIVITY_NAME = "MainActivity";
    private static final String NAME = "activity name";
    private static final int DEFAULT_NUMBER = 100;

    private static RecyclerView locksRecycler;
    private static LocksAdapter locksAdapter;
    private LocksViewModel locksViewModel;
    private static List<LocksEntityData.Lock> locksList;
    private static boolean firstStarted = true;
    private ImageView settingsButton;
    private OnOpenFragmentManager onOpenFragmentManager;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment_layout, container, false);
        locksRecycler = root.findViewById(R.id.lw);
        settingsButton = root.findViewById(R.id.settings_main);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onOpenFragmentManager = (OnOpenFragmentManager) getActivity();

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        locksRecycler.addItemDecoration(itemDecoration);

        locksViewModel = ViewModelProviders.of(getActivity(), App.getLocksModelFactory())
                .get(LocksViewModel.class);

        LocksAdapter.OnLockClickListener onLockClickListener = lock -> onOpenFragmentManager.onLockUsersFragmentOpen(lock.getLId());
        locksAdapter = new LocksAdapter(onLockClickListener, true);

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            intent.putExtra(NAME, ACTIVITY_NAME);
            startActivity(intent);
        });

        //Получаем список замков от сервера
        observeLocks();

        //Обновляем данные
        updateLocks();

        if (firstStarted) {
            //Загружаем список пользователей
            loadUsers();
            //Загружаем список доступов
            loadAccesses();
            firstStarted = false;
        }

    }


    private void observeLocks() {
        LiveData<List<LocksEntityData.Lock>> listLiveData = locksViewModel.getData();
        listLiveData.observe(this, locks -> {
            locksList = locks;
            locksRecycler.setAdapter(locksAdapter);
            locksAdapter.replaceLocks(locks);
        });
    }

    private void updateLocks() {
        locksViewModel.loadData();
    }

    private void loadUsers() {
        UsersViewModel usersViewModel = ViewModelProviders.of(getActivity(), App.getUsersModelFactory())
                .get(UsersViewModel.class);
        usersViewModel.getData();
    }

    private void loadAccesses() {
        AccessViewModel accessViewModel = ViewModelProviders.of(getActivity(), App.getAccessModelFactory())
                .get(AccessViewModel.class);
        accessViewModel.getData();
    }


    private void authorizationActivityStart() {
        Intent intent = new Intent(getActivity(), AuthorizationActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


}
