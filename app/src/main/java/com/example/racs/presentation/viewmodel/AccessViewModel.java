package com.example.racs.presentation.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.racs.data.api.App;
import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.repository.AccessesRepository;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.domain.usecases.addusecases.AddAccess;
import com.example.racs.domain.usecases.deleteusecases.DeleteAccess;
import com.example.racs.domain.usecases.getusecases.GetAccesses;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AccessViewModel extends ViewModel {

    private MutableLiveData<List<AccessEntity.AccPOJO>> accessesData;
    private AccessesRepository accessesRepository = new AccessesRepository();
    private GetAccesses usecaseGetAccesses;
    private AddAccess usecaseAddAccess;
    private DeleteAccess usecaseDeleteAccess;
    private SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final int DEFAULT_NUMBER = 50;
    private boolean deleted;
    public static final String ADDED = "ADDED_NEW_VALUE";
    private OnCompleteListener<List<AccessEntity.AccPOJO>> onCompleteListener;

    public LiveData<List<AccessEntity.AccPOJO>> getData(){
        if (accessesData == null){
            accessesData = new MutableLiveData<>();
            loadData();
        }
        return accessesData;
    }

    public void loadData() {
        if (usecaseGetAccesses == null){
            settings = App.getSettings();
            usecaseGetAccesses = new GetAccesses(accessesRepository, new OnCompleteListener<List<AccessEntity.AccPOJO>>() {
                @Override
                public void onComplete(List<AccessEntity.AccPOJO> smt) {
                    accessesData.setValue(smt);
                }
            });
        }
        usecaseGetAccesses.getAccesses(settings.getString(ACCESS_TOKEN, ""), DEFAULT_NUMBER);
    }

    public void deleteAccess(int id){
        if (usecaseDeleteAccess == null){
            usecaseDeleteAccess = new DeleteAccess(accessesRepository, new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(Boolean smt) {
                    deleted = smt;
                    loadData();
                }
            });
        }
        usecaseDeleteAccess.deleteUser(settings.getString(ACCESS_TOKEN, ""), id);
    }

    public void addAccess(AccessPostEntity body, final Context context){
        if (usecaseAddAccess == null){
            usecaseAddAccess = new AddAccess(accessesRepository, new OnCompleteListener<Boolean>() {
                @Override
                public void onComplete(Boolean smt) {
                    if (smt){
                        loadData();
                        Intent intent = new Intent();
                        intent.putExtra(ADDED, smt);
                        ((AppCompatActivity) context).setResult(RESULT_OK, intent);
                        ((AppCompatActivity) context).finish();
                    }
                }
            });
        }
        usecaseAddAccess.addAccess(settings.getString(ACCESS_TOKEN, ""), body);
    }
}
