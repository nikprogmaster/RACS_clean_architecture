package com.example.racs.domain.usecases.addusecases;

import android.os.AsyncTask;

import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.repository.IUsersReposytory;
import com.example.racs.domain.usecases.OnCompleteListener;

public class AddUser {

    private final IUsersReposytory repository;
    private final UserPostEntity body;
    private final String token;
    private OnCompleteListener<Boolean> onCompleteListener;

    public AddUser(IUsersReposytory repository, UserPostEntity body, String token, OnCompleteListener<Boolean> onCompleteListener) {
        this.repository = repository;
        this.body = body;
        this.token = token;
        this.onCompleteListener = onCompleteListener;
    }

    public void addUser() {
        AddUserAsyncTask asyncTask = new AddUserAsyncTask(repository, body, token, onCompleteListener);
        asyncTask.execute();
    }

    static class AddUserAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private final IUsersReposytory reposytory;
        private final UserPostEntity body;
        private final String token;
        private OnCompleteListener<Boolean> onCompleteListener;

        AddUserAsyncTask(IUsersReposytory reposytory, UserPostEntity body, String token, OnCompleteListener<Boolean> onCompleteListener) {
            this.reposytory = reposytory;
            this.body = body;
            this.token = token;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return reposytory.addUser(token, body);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            onCompleteListener.onComplete(aBoolean);
            super.onPostExecute(aBoolean);
        }
    }
}
