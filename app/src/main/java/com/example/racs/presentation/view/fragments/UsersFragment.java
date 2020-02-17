package com.example.racs.presentation.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;
import com.example.racs.data.api.App;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.presentation.view.activities.AddUserActivity;
import com.example.racs.presentation.view.activities.OnAccessListClickListener;
import com.example.racs.presentation.view.adapters.UsersAdapter;
import com.example.racs.presentation.viewmodel.OnStopLoadingListener;
import com.example.racs.presentation.viewmodel.UsersViewModel;

import java.util.List;

public class UsersFragment extends Fragment {

    private static RecyclerView usersRecycler;
    private static UsersAdapter usersAdapter;
    private TextView undlined;
    private static boolean isAdded = false;
    private static boolean isDeleted = false;
    private static List<UsersEntity.User> usersList;
    private Button add_btn;
    public static final String ADDED_NEW_VALUE = "ADDED NEW VALUE";
    private static final int DEFAULT_NUMBER = 100;
    private OnAccessListClickListener onAccessListClickListener;
    private LiveData<List<UsersEntity.User>> userLiveData;
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
        add_btn = root.findViewById(R.id.add_user1);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onAccessListClickListener = (OnAccessListClickListener) getActivity();
        usersAdapter = new UsersAdapter(new UsersAdapter.UsersAdapterListener() {
            @Override
            public void onBinClick(int id) {
                usersViewModel.deleteUser(id);
                isDeleted = usersViewModel.isDeleted();
            }
        });
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL);
        usersRecycler.addItemDecoration(itemDecoration);
        usersRecycler.setAdapter(usersAdapter);

        observeUsers();

        initScrollListener();

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddUserActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        undlined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAccessListClickListener.onAccessClick();
            }
        });

        updateUsers(userLiveData.getValue().size());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        isAdded = data.getBooleanExtra(ADDED_NEW_VALUE, isAdded);
        observeUsers();
    }

    private void updateUsers(int count) {
        if(usersViewModel != null){
            usersViewModel.loadData(count);
        }
    }

    private void observeUsers() {
        usersViewModel = App.getUsersViewModel();
        Log.i("Observe", "сюда пришли");
        userLiveData = usersViewModel.getData(DEFAULT_NUMBER);
        usersList = userLiveData.getValue();
        userLiveData.observe(this, new Observer<List<UsersEntity.User>>() {
            @Override
            public void onChanged(List<UsersEntity.User> users) {
                usersList = users;
                usersAdapter.replaceUsers(usersList);
            }
        });
    }

    private void initScrollListener(){
        RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                usersViewModel.setOnStopLoadingListener(new OnStopLoadingListener() {
                    @Override
                    public void onStop() {
                        onStop = true;
                    }
                });
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();//смотрим сколько элементов на экране
                int totalItemCount = layoutManager.getItemCount();//сколько всего элементов
                int firstVisibleItems = layoutManager.findFirstVisibleItemPosition();//какая позиция первого элемента

                if (!App.getUsersViewModel().isLoading() && !onStop) {//проверяем, грузим мы что-то или нет, эта переменная должна быть вне класса  OnScrollListener
                    Log.i("scrollListener", "в первом if");
                    Log.i("visibleCount+firstVisib", String.valueOf(visibleItemCount+firstVisibleItems));
                    Log.i("totalItemCount", String.valueOf(totalItemCount));
                    if ( (visibleItemCount+firstVisibleItems) > totalItemCount - 50) {
                        Log.i("Сколько вызываем", "= " + (userLiveData.getValue().size() + DEFAULT_NUMBER));

                        App.getUsersViewModel().loadData(userLiveData.getValue().size() + DEFAULT_NUMBER);
                    }

                }
            }
        };
        usersRecycler.addOnScrollListener(scrollListener);
    }

}
