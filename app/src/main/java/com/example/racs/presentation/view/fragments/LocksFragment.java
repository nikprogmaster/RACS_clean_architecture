package com.example.racs.presentation.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;
import com.example.racs.data.api.App;
import com.example.racs.data.entities.LocksEntity;
import com.example.racs.presentation.view.activities.LockActivity;
import com.example.racs.presentation.view.adapters.LocksAdapter;
import com.example.racs.presentation.viewmodel.LocksViewModel;

import java.util.List;

public class LocksFragment extends Fragment {

    private static RecyclerView locks_recycler;
    private TextView tw;
    private static LocksAdapter locks_adapter;
    public static List<LocksEntity.Lock> locksList;
    private LocksAdapter.OnDeleteClickListener onDeleteClickListener = new LocksAdapter.OnDeleteClickListener() {
        @Override
        public void onDelete(int id) {
            LocksViewModel locksViewModel = App.getLocksViewModel();
            locksViewModel.deleteLock(id);
        }
    };

    public static LocksFragment newInstance() {

        Bundle args = new Bundle();

        LocksFragment fragment = new LocksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_locks, container, false);
        tw = root.findViewById(R.id.locks_list);
        locks_recycler = root.findViewById(R.id.locks_recycler);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LocksAdapter.OnLockClickListener onLockClickListener = new LocksAdapter.OnLockClickListener() {
            @Override
            public void onLockClick(LocksEntity.Lock lock) {
                Intent intent = new Intent(getActivity(), LockActivity.class);
                intent.putExtra(LockActivity.EXTRA_LOCKID, lock.getLId());
                startActivity(intent);
            }
        };
        locks_adapter = new LocksAdapter(onLockClickListener,  false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        locks_recycler.addItemDecoration(itemDecoration);
        locks_recycler.setAdapter(locks_adapter);

        observeLocks();

        updateLocks();
    }



    private void observeLocks() {
        LocksViewModel locksViewModel = App.getLocksViewModel();
        LiveData<List<LocksEntity.Lock>> listLiveData = locksViewModel.getData();
        listLiveData.observe(this, new Observer<List<LocksEntity.Lock>>() {
            @Override
            public void onChanged(List<LocksEntity.Lock> locks) {
                locksList = locks;
                locks_adapter.replaceLocks(locksList);
            }
        });
    }

    private void updateLocks(){
        LocksViewModel locksViewModel = App.getLocksViewModel();
        locksViewModel.loadData();
    }
}
