package com.example.racs.presentation.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.racs.App;
import com.example.racs.R;
import com.example.racs.model.data.UserPostEntityData;
import com.example.racs.presentation.view.activities.OnCloseFragmentManager;
import com.example.racs.presentation.viewmodel.UsersViewModel;

public class AddUserFragment extends Fragment {

    private TextView name;
    private TextView surname;
    private TextView patronymic;
    private TextView card_id;
    private TextView email;
    private Spinner role;
    private Button add_btn;
    private UsersViewModel usersViewModel;
    private OnCloseFragmentManager onCloseFragmentManager;

    public static AddUserFragment newInstance() {

        Bundle args = new Bundle();

        AddUserFragment fragment = new AddUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_add_user, container, false);
        bindViews(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        usersViewModel = ViewModelProviders.of(getActivity(), App.getUsersModelFactory())
                .get(UsersViewModel.class);

        onCloseFragmentManager = (OnCloseFragmentManager) getActivity();

        add_btn.setOnClickListener(view -> {

            final UserPostEntityData body = new UserPostEntityData();
            body.setFirstName(String.valueOf(name.getText()));
            body.setLastName(String.valueOf(surname.getText()));
            body.setPatronymic(String.valueOf(patronymic.getText()));
            body.setCardId(String.valueOf(card_id.getText()));
            body.setEmail(String.valueOf(email.getText()));
            body.setRole(role.getSelectedItem().toString());

            usersViewModel.addUser(body);

            onCloseFragmentManager.onClose();

        });
    }

    private void bindViews(View root) {
        name = root.findViewById(R.id.first_name);
        surname = root.findViewById(R.id.last_name);
        patronymic = root.findViewById(R.id.patronymic);
        card_id = root.findViewById(R.id.ed_card_id);
        email = root.findViewById(R.id.email);
        role = root.findViewById(R.id.role);
        add_btn = root.findViewById(R.id.add_user_btn);
    }
}
