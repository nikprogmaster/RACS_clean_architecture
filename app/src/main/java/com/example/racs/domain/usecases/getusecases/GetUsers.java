package com.example.racs.domain.usecases.getusecases;

import android.os.AsyncTask;

import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.IUsersReposytory;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;
import java.util.concurrent.Executor;

public class GetUsers {

    private IUsersReposytory repository;
    private OnCompleteListener<List<UsersEntity.User>> onCompleteListener;

    public GetUsers(IUsersReposytory repository, OnCompleteListener<List<UsersEntity.User>> onCompleteListener) {
        this.repository = repository;
        this.onCompleteListener = onCompleteListener;
    }


    public void getUsers(String token, int count) {
        repository.getUsers(token, count, onCompleteListener);
    }


   /* static class GetUsersAsyncTask extends AsyncTask<Void, Void, List<UsersEntity.User>> {

        private final IUsersReposytory repository;
        private final String token;
        private final int count;
        private OnCompleteListener<List<UsersEntity.User>> onCompleteListener;

        GetUsersAsyncTask(IUsersReposytory repository, String token, int count, OnCompleteListener<List<UsersEntity.User>> onCompleteListener) {
            this.repository = repository;
            this.token = token;
            this.count = count;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        protected List<UsersEntity.User> doInBackground(Void... voids) {
            return repository.getUsers(token, count);
        }

        @Override
        protected void onPostExecute(List<UsersEntity.User> users) {
            onCompleteListener.onComplete(users);
            super.onPostExecute(users);
        }
    }*/

}
