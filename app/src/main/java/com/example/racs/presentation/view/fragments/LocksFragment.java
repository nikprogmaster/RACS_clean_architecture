package com.example.racs.presentation.view.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.racs.R;
import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.repository.LocksRepository;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.domain.usecases.deleteusecases.DeleteLock;
import com.example.racs.domain.usecases.getusecases.GetLocks;
import com.example.racs.presentation.view.activities.LockActivity;
import com.example.racs.presentation.view.adapters.LocksAdapter;
import com.example.racs.presentation.viewmodel.LocksViewModel;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LocksFragment extends Fragment {

    private static RecyclerView locks_recycler;
    private TextView tw;
    private static final int DEFAULT_NUMBER = 50;
    private static LocksAdapter locks_adapter;
    public static List<LocksEntity.Lock> locksList;
    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";
    private static GetLocks usecaseGetLocks;
    private static DeleteLock usecaseDeleteLock;
    private static final LocksRepository locksRepository = new LocksRepository();
    private LocksAdapter.OnDeleteClickListener onDeleteClickListener = new LocksAdapter.OnDeleteClickListener() {
        @Override
        public void onDelete(int id) {
            usecaseDeleteLock = new DeleteLock(locksRepository, settings.getString(ACCESS_TOKEN, ""), id, new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(Boolean smt) {
                    getLocks(locksList.size());
                }
            });
            usecaseDeleteLock.deleteLock();
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
        settings = getActivity().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

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

        getLocks(DEFAULT_NUMBER);

    }

    //  no usage
    /*public void onBinClick(View view) {
        View parent = (View) view.getParent();
        TextView t = (TextView) parent.findViewById(R.id.tw_id);
        String s = String.valueOf(t.getText());
        int id = Integer.valueOf(s);
        usecaseDeleteLock = new DeleteLock(locksRepository, settings.getString(ACCESS_TOKEN, ""), id, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(Boolean smt) {
                getLocks(locksList.size());
            }
        });
        usecaseDeleteLock.deleteLock();

    }*/


    public void getLocks(int count) {
        LocksViewModel locksViewModel = ViewModelProviders.of(this).get(LocksViewModel.class);
        LiveData<List<LocksEntity.Lock>> listLiveData = locksViewModel.getData();
        listLiveData.observe(this, new Observer<List<LocksEntity.Lock>>() {
            @Override
            public void onChanged(List<LocksEntity.Lock> locks) {
                locksList = locks;
                locks_adapter.replaceLocks(locksList);
            }
        });
    }
}
