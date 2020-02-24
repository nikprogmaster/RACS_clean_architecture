package com.example.racs.presentation.view.fragments;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.App;
import com.example.racs.R;
import com.example.racs.model.data.LocksEntityData;
import com.example.racs.presentation.view.activities.OnOpenFragmentManager;
import com.example.racs.presentation.view.adapters.LocksAdapter;
import com.example.racs.presentation.viewmodel.LocksViewModel;

import java.util.List;

public class LocksFragment extends Fragment {

    private static RecyclerView locksRecycler;
    private TextView tw;
    private static LocksAdapter locksAdapter;
    private static List<LocksEntityData.Lock> locksList;
    private LocksViewModel locksViewModel;
    private OnOpenFragmentManager onOpenFragmentManager;
    private LocksAdapter.OnDeleteClickListener onDeleteClickListener = new LocksAdapter.OnDeleteClickListener() {
        @Override
        public void onDelete(int id) {
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
        locksRecycler = root.findViewById(R.id.locks_recycler);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onOpenFragmentManager = (OnOpenFragmentManager) getActivity();

        locksViewModel = ViewModelProviders.of(getActivity(), App.getLocksModelFactory())
                .get(LocksViewModel.class);

        LocksAdapter.OnLockClickListener onLockClickListener = lock -> onOpenFragmentManager.onLockUsersFragmentOpen(lock.getLId());
        locksAdapter = new LocksAdapter(onLockClickListener, false);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        locksRecycler.addItemDecoration(itemDecoration);
        locksRecycler.setAdapter(locksAdapter);

        observeLocks();

        updateLocks();
    }


    private void observeLocks() {
        LiveData<List<LocksEntityData.Lock>> listLiveData = locksViewModel.getData();
        locksList = listLiveData.getValue();
        if (locksList != null){
            locksAdapter.replaceLocks(locksList);
        }
        listLiveData.observe(this, locks -> {
            locksList = locks;
            locksAdapter.replaceLocks(locksList);
        });
    }

    private void updateLocks() {
        locksViewModel.loadData();
    }

}
