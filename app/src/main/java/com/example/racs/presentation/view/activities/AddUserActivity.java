package com.example.racs.presentation.view.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.racs.R;
import com.example.racs.data.api.App;
import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.repository.UsersRepository;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.domain.usecases.addusecases.AddUser;
import com.example.racs.presentation.viewmodel.UsersViewModel;

public class AddUserActivity extends AppCompatActivity {

    private TextView name, surname, patronymic, card_id, email;
    private Spinner role;
    private Button add_btn;
    private static AddUser usecaseAddUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        bindViews();



        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final UserPostEntity body = new UserPostEntity();
                body.setFirstName(String.valueOf(name.getText()));
                body.setLastName(String.valueOf(surname.getText()));
                body.setPatronymic(String.valueOf(patronymic.getText()));
                body.setCardId(String.valueOf(card_id.getText()));
                body.setEmail(String.valueOf(email.getText()));
                body.setRole(role.getSelectedItem().toString());

                UsersViewModel usersViewModel = App.getUsersViewModel();
                usersViewModel.addUser(body, AddUserActivity.this);

            }
        });
    }

    private void bindViews() {
        name = findViewById(R.id.first_name);
        surname = findViewById(R.id.last_name);
        patronymic = findViewById(R.id.patronymic);
        card_id = findViewById(R.id.ed_card_id);
        email = findViewById(R.id.email);
        role = findViewById(R.id.role);
        add_btn = findViewById(R.id.add_user_btn);
    }
}
