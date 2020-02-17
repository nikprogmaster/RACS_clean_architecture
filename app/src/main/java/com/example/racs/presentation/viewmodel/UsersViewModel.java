package com.example.racs.presentation.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.racs.data.api.App;
import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.UsersRepository;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.domain.usecases.addusecases.AddUser;
import com.example.racs.domain.usecases.deleteusecases.DeleteUser;
import com.example.racs.domain.usecases.getusecases.GetUsers;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class UsersViewModel extends ViewModel {

    private MutableLiveData<List<UsersEntity.User>> usersData;
    private UsersRepository usersRepository = new UsersRepository();
    private GetUsers usecaseGetUsers;
    private DeleteUser usecaseDeleteUser;
    private AddUser usecaseAddUser;
    private SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final int DEFAULT_NUMBER = 100;
    private OnCompleteListener<List<UsersEntity.User>> onCompleteListener;
    public static final String ADDED_NEW_VALUE = "ADDED NEW VALUE";
    private boolean isLoading = false;
    private boolean added;
    private boolean deleted;
    private OnStopLoadingListener onStopLoadingListener;

    public void setOnStopLoadingListener(OnStopLoadingListener onStopLoadingListener) {
        this.onStopLoadingListener = onStopLoadingListener;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public LiveData<List<UsersEntity.User>> getData(int count){
        if (usersData == null){
            usersData = new MutableLiveData<>();
            loadData(count);
        }
        return usersData;
    }


    public void loadData(final int count) {
        if (usecaseGetUsers == null){
            settings = App.getSettings();
            usecaseGetUsers = new GetUsers(usersRepository, new OnCompleteListener<List<UsersEntity.User>>() {
                @Override
                public void onComplete(List<UsersEntity.User> smt) {
                    if (smt != null){
                        usersData.setValue(smt);
                        if (smt.size() < count){
                            onStopLoadingListener.onStop();
                        }
                    }
                    isLoading = false;
                }
            });
        }
        usecaseGetUsers.getUsers(settings.getString(ACCESS_TOKEN, ""), count);
        isLoading = true;
    }



    public void deleteUser(int id){
        if (usecaseDeleteUser == null){
            usecaseDeleteUser = new DeleteUser(usersRepository, new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(Boolean smt) {
                    loadData(usersData.getValue().size());
                    deleted = smt;
                }
            });
        }
        usecaseDeleteUser.deleteUser(settings.getString(ACCESS_TOKEN, ""), id);
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void addUser(UserPostEntity body, final Context context){

        if (usecaseAddUser == null){
            usecaseAddUser = new AddUser(usersRepository, new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(Boolean smt) {
                    loadData(usersData.getValue().size());
                    if (smt) {
                        Toast.makeText(context, "Пользователь успешно добавлен!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra(ADDED_NEW_VALUE, true);
                        ((AppCompatActivity) context).setResult(RESULT_OK, intent);
                        ((AppCompatActivity) context).finish();
                    }
                }
            });
        }
        usecaseAddUser.addUser(settings.getString(ACCESS_TOKEN, ""), body);

    }
}
