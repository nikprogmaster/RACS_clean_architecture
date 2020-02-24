package com.example.racs.presentation.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.App;
import com.example.racs.R;
import com.example.racs.model.data.UsersEntityData;
import com.example.racs.presentation.view.activities.OnOpenFragmentManager;
import com.example.racs.presentation.view.adapters.UsersAdapter;
import com.example.racs.presentation.viewmodel.UsersViewModel;

import java.util.List;

public class UsersFragment extends Fragment {

    private static final String ADDED_NEW_VALUE = "ADDED NEW VALUE";
    private static final int DEFAULT_NUMBER = 100;

    private static RecyclerView usersRecycler;
    private static UsersAdapter usersAdapter;
    private static boolean isAdded = false;
    private static boolean isDeleted = false;
    private static List<UsersEntityData.User> usersList;
    private Button addButton;
    private TextView undlined;
    private OnOpenFragmentManager onOpenFragmentManager;
    private LiveData<List<UsersEntityData.User>> userLiveData;
    private UsersViewModel usersViewModel;
    private boolean onStop = false;


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
        addButton = root.findViewById(R.id.add_user1);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onOpenFragmentManager = (OnOpenFragmentManager) getActivity();
        usersAdapter = new UsersAdapter(id -> {
            usersViewModel.deleteUser(id);
            isDeleted = usersViewModel.isDeleted();
        });
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        usersRecycler.addItemDecoration(itemDecoration);
        usersRecycler.setAdapter(usersAdapter);

        observeUsers();

        initScrollListener();

        addButton.setOnClickListener(view -> onOpenFragmentManager.onAddUserFragmentOpen());

        undlined.setOnClickListener(v -> onOpenFragmentManager.onAccessFragmentOpen());

        if (!usersViewModel.isLoading()) {
            updateUsers(usersViewModel.getPagesCount() * 100);
        }


    }

    private void updateUsers(int count) {
        if (usersViewModel != null) {
            usersViewModel.loadData(count);
        }
    }

    private void observeUsers() {
        usersViewModel = ViewModelProviders.of(getActivity(), App.getUsersModelFactory())
                .get(UsersViewModel.class);
        userLiveData = usersViewModel.getData();
        if (userLiveData.getValue() != null) {
            usersList = userLiveData.getValue();
            Log.i("observe", String.valueOf(usersList.size()));
            usersAdapter.replaceUsers(usersList);
        }
        userLiveData.observe(this, users -> {
            Log.i("onChanged", String.valueOf(users.size()));
            if (usersList == null) {
                usersList = users;
                usersAdapter.replaceUsers(usersList);
            } else {
                if (users.size() >= usersList.size() - 5) {
                    usersList = users;
                    usersAdapter.replaceUsers(usersList);
                }
            }
        });
    }

    private void initScrollListener() {
        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();//смотрим сколько элементов на экране
                int totalItemCount = layoutManager.getItemCount();//сколько всего элементов
                int firstVisibleItems = layoutManager.findFirstVisibleItemPosition();//какая позиция первого элемента

                if (!usersViewModel.isLoading() && !usersViewModel.isOnStop()) {//проверяем, грузим мы что-то или нет, эта переменная должна быть вне класса  OnScrollListener
                    Log.i("visibleCount+firstVisib", String.valueOf(visibleItemCount + firstVisibleItems));
                    Log.i("totalItemCount", String.valueOf(totalItemCount));
                    if ((visibleItemCount + firstVisibleItems) > totalItemCount - 50) {
                        Log.i("Сколько вызываем", "= " + (userLiveData.getValue().size() + DEFAULT_NUMBER));

                        usersViewModel.loadData(userLiveData.getValue().size() + DEFAULT_NUMBER);
                    }

                }
            }
        };
        usersRecycler.addOnScrollListener(scrollListener);
    }


}
