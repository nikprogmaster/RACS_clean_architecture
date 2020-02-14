package com.example.racs.presentation.view.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;
import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.AccessesRepository;
import com.example.racs.data.repository.LocksRepository;
import com.example.racs.data.repository.UsersRepository;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.domain.usecases.deleteusecases.DeleteAccess;
import com.example.racs.domain.usecases.getusecases.GetAccesses;
import com.example.racs.domain.usecases.getusecases.GetLocks;
import com.example.racs.domain.usecases.getusecases.GetUsers;
import com.example.racs.presentation.view.activities.AddAccessActivity;
import com.example.racs.presentation.view.adapters.AccessAdapter;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AccessFragment extends Fragment {

    private static final int DEFAULT_NUMBER = 50;
    private static RecyclerView accessRecycler;
    private static AccessAdapter accessAdapter;
    private static List<AccessEntity.AccPOJO> accesses;
    private static boolean isAdded = false;
    private Button add_btn;
    private ImageView back;
    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";
    private static final AccessesRepository accessesRepository = new AccessesRepository();
    private static GetAccesses usecaseGetAccesses;
    private static DeleteAccess usecaseDeleteAccess;
    private static List<LocksEntity.Lock> locks;
    private static List<UsersEntity.User> users;
    private static final LocksRepository locksRepository = new LocksRepository();
    private static final UsersRepository usersRepository = new UsersRepository();
    private GetLocks usecaseGetLocks;
    private GetUsers usecaseGetUsers;
    private OnCompleteListener<Boolean> onCompleteListener;
    private AccessAdapter.OnDeleteListener onDeleteListener = new AccessAdapter.OnDeleteListener() {
        @Override
        public void onDelete(int position) {
            usecaseDeleteAccess = new DeleteAccess(accessesRepository, settings.getString(ACCESS_TOKEN, ""), position, new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(Boolean smt) {
                    getAccesses(DEFAULT_NUMBER);
                }
            });
            usecaseDeleteAccess.deleteUser();
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireFragmentManager().popBackStack();
            }
        });

        settings = getActivity().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        onCompleteListener = new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(Boolean smt) {
                accessAdapter = new AccessAdapter(locks, users, onDeleteListener);
                getAccesses(DEFAULT_NUMBER);
            }
        };

        getUsers(DEFAULT_NUMBER);
        getLocks(DEFAULT_NUMBER);


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
        getAccesses(isAdded ? accesses.size() + 1 : accesses.size());

    }

    public static void getAccesses(int count) {
        usecaseGetAccesses = new GetAccesses(accessesRepository, settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER, new OnCompleteListener<List<AccessEntity.AccPOJO>>() {
            @Override
            public void onComplete(List<AccessEntity.AccPOJO> smt) {
                accesses = smt;
                accessRecycler.setAdapter(accessAdapter);
                accessAdapter.replaceAccesses(accesses);
            }
        });
        usecaseGetAccesses.getAccesses();

    }

    public void getUsers(int count) {
        usecaseGetUsers = new GetUsers(usersRepository, settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER, new OnCompleteListener<List<UsersEntity.User>>() {
            @Override
            public void onComplete(List<UsersEntity.User> smt) {
                users = smt;

            }
        });
        usecaseGetUsers.getUsers();
    }

    public void getLocks(int count) {
        usecaseGetLocks = new GetLocks(locksRepository, settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER, new OnCompleteListener<List<LocksEntity.Lock>>() {
            @Override
            public void onComplete(List<LocksEntity.Lock> smt) {
                locks = smt;
                onCompleteListener.onComplete(true);
            }
        });
        usecaseGetLocks.getLocks();
    }

}
