package com.example.racs.view.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;
import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.repository.LocksRepository;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.domain.usecases.getusecases.GetLocks;
import com.example.racs.view.activities.AuthorizationActivity;
import com.example.racs.view.activities.LockActivity;
import com.example.racs.view.activities.SettingsActivity;
import com.example.racs.view.adapters.LocksAdapter;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment {


    private static RecyclerView locks_recycler;
    private static LocksAdapter locks_adapter;
    private static List<LocksEntity.Lock> locks;
    private static final int DEFAULT_NUMBER = 50;
    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";
    private static final String ACTIVITY_NAME = "MainActivity";
    private static final String NAME = "activity name";
    private ImageView settingsButton;
    private static final LocksRepository locksRepository = new LocksRepository();
    private GetLocks usecaseGetLocks;



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
        settingsButton = root.findViewById(R.id.settings);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        locks_recycler.addItemDecoration(itemDecoration);

        //Получаем настройки
        settings = requireContext().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

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
        getLocks(DEFAULT_NUMBER);
    }


    public void getLocks(int count) {
        usecaseGetLocks = new GetLocks(locksRepository, settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER, new OnCompleteListener<List<LocksEntity.Lock>>() {
            @Override
            public void onComplete(List<LocksEntity.Lock> smt) {
                locks = smt;
                locks_recycler.setAdapter(locks_adapter);
                locks_adapter.replaceLocks(locks);
            }
        });
        usecaseGetLocks.getLocks();
    }




    private void authorizationActivityStart (){
        Intent intent = new Intent(getActivity(), AuthorizationActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


}
