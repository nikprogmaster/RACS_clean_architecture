package com.example.racs.presentation.view.fragments;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.App;
import com.example.racs.R;
import com.example.racs.model.data.AccessEntityData;
import com.example.racs.model.data.LocksEntityData;
import com.example.racs.model.data.UsersEntityData;
import com.example.racs.presentation.view.activities.OnCloseFragmentManager;
import com.example.racs.presentation.view.activities.OnOpenFragmentManager;
import com.example.racs.presentation.view.adapters.AccessAdapter;
import com.example.racs.presentation.viewmodel.AccessViewModel;
import com.example.racs.presentation.viewmodel.LocksViewModel;
import com.example.racs.presentation.viewmodel.UsersViewModel;

import java.util.List;

public class AccessFragment extends Fragment {

    private static final int DEFAULT_NUMBER = 100;

    private static RecyclerView accessRecycler;
    private static AccessAdapter accessAdapter;
    private static List<AccessEntityData.Access> accesses;
    private static boolean isAdded = false;
    private Button addButton;
    private ImageView back;
    private LiveData<List<UsersEntityData.User>> usersData;
    private OnCloseFragmentManager onCloseFragmentManager;
    private static List<LocksEntityData.Lock> lockList;
    private static List<UsersEntityData.User> usersList;
    private AccessViewModel accessViewModel;
    private UsersViewModel usersViewModel;
    private LocksViewModel locksViewModel;
    private LiveData<List<AccessEntityData.Access>> accessesLiveData;
    private OnOpenFragmentManager onOpenFragmentManager;
    private AccessAdapter.OnDeleteListener onDeleteListener = new AccessAdapter.OnDeleteListener() {
        @Override
        public void onDelete(int position) {
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
        addButton = root.findViewById(R.id.button2);
        back = root.findViewById(R.id.back);
        accessRecycler = root.findViewById(R.id.access_list);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        accessRecycler.addItemDecoration(itemDecoration);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onOpenFragmentManager = (OnOpenFragmentManager) getActivity();
        onCloseFragmentManager = (OnCloseFragmentManager) getActivity();

        back.setOnClickListener(v -> onCloseFragmentManager.onClose());

        accessViewModel = ViewModelProviders.of(getActivity(), App.getAccessModelFactory())
                .get(AccessViewModel.class);


        observeLocks();
        observeUsers();
        observeAccesses();


        updateLocks();
        updateUsers();
        updateAccesses();

        //initScrollListener();

        addButton.setOnClickListener(view -> onOpenFragmentManager.onAddAccessFragmentOpen());
    }


    private void observeAccesses() {
        accessesLiveData = accessViewModel.getData();
        accesses = accessesLiveData.getValue();
        if (accessAdapter != null) {
            accessAdapter.replaceAccesses(accesses);
        }
        accessesLiveData.observe(this, accesses -> {
            AccessFragment.accesses = accesses;
            accessAdapter = new AccessAdapter(lockList, usersList, onDeleteListener);
            accessRecycler.setAdapter(accessAdapter);
            accessAdapter.replaceAccesses(AccessFragment.accesses);
        });
    }

    private void observeUsers() {
        usersViewModel = ViewModelProviders.of(getActivity(), App.getUsersModelFactory())
                .get(UsersViewModel.class);
        usersData = usersViewModel.getData();
        usersList = usersData.getValue();
        usersData.observe(this, users -> usersList = users);
    }

    private void observeLocks() {
        locksViewModel = ViewModelProviders.of(getActivity(), App.getLocksModelFactory())
                .get(LocksViewModel.class);
        LiveData<List<LocksEntityData.Lock>> locksData = locksViewModel.getData();
        lockList = locksData.getValue();
        locksData.observe(this, locks -> lockList = locks);
    }

    private void updateAccesses() {
        accessViewModel.loadData(DEFAULT_NUMBER);

    }

    private void updateUsers() {
        usersViewModel.loadData(usersViewModel.getPagesCount() * 100);
    }

    private void updateLocks() {
        locksViewModel.loadData();
    }

   /* private void initScrollListener(){
        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();//смотрим сколько элементов на экране
                int totalItemCount = layoutManager.getItemCount();//сколько всего элементов
                int firstVisibleItems = layoutManager.findFirstVisibleItemPosition();//какая позиция первого элемента

                if (!accessViewModel.isLoading() && !accessViewModel.isOnStop()) {//проверяем, грузим мы что-то или нет, эта переменная должна быть вне класса  OnScrollListener
                    if ( (visibleItemCount+firstVisibleItems) > totalItemCount - 50) {
                        Log.i("Сколько вызываем", "= " + (accessesLiveData.getValue().size() + DEFAULT_NUMBER));
                        usersViewModel.loadData(accessesLiveData.getValue().size() + DEFAULT_NUMBER);
                    }

                }
            }
        };
        accessRecycler.addOnScrollListener(scrollListener);
    }*/

}
