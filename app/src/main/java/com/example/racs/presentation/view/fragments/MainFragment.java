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

import com.example.racs.R;
import com.example.racs.data.entities.LocksEntity;
import com.example.racs.presentation.view.activities.AuthorizationActivity;
import com.example.racs.presentation.view.activities.LockActivity;
import com.example.racs.presentation.view.activities.SettingsActivity;
import com.example.racs.presentation.view.adapters.LocksAdapter;
import com.example.racs.presentation.viewmodel.LocksViewModel;

import java.util.List;

public class MainFragment extends Fragment {

    private static RecyclerView locks_recycler;
    private static LocksAdapter locks_adapter;
    private static List<LocksEntity.Lock> locksList;
    private static final String ACTIVITY_NAME = "MainActivity";
    private static final String NAME = "activity name";
    private ImageView settingsButton;


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
        locks_recycler = root.findViewById(R.id.lw);
        settingsButton = root.findViewById(R.id.settings_main);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        locks_recycler.addItemDecoration(itemDecoration);


        LocksAdapter.OnLockClickListener onLockClickListener = new LocksAdapter.OnLockClickListener() {
            @Override
            public void onLockClick(LocksEntity.Lock lock) {
                Intent intent = new Intent(getActivity(), LockActivity.class);
                intent.putExtra(LockActivity.EXTRA_LOCKID, lock.getLId());
                startActivity(intent);
            }
        };
        locks_adapter = new LocksAdapter(onLockClickListener, true);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                intent.putExtra(NAME, ACTIVITY_NAME);
                startActivity(intent);
            }
        });

        //Получаем список замков от сервера
        getLocks();
    }


    public void getLocks() {
        LocksViewModel locksViewModel = ViewModelProviders.of(this).get(LocksViewModel.class);
        LiveData<List<LocksEntity.Lock>> listLiveData = locksViewModel.getData();
        listLiveData.observe(this, new Observer<List<LocksEntity.Lock>>() {
            @Override
            public void onChanged(List<LocksEntity.Lock> locks) {
                locksList = locks;
                locks_recycler.setAdapter(locks_adapter);
                locks_adapter.replaceLocks(locks);
            }
        });
    }


    private void authorizationActivityStart (){
        Intent intent = new Intent(getActivity(), AuthorizationActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
