package com.example.racs.domain.usecases.deleteusecases;

import android.os.AsyncTask;

import com.example.racs.data.repository.IUsersReposytory;
import com.example.racs.domain.usecases.OnCompleteListener;

public class DeleteUser {

    private IUsersReposytory repository;
    private String token;
    private int id;
    private OnCompleteListener<Boolean> onCompleteListener;

    public DeleteUser(IUsersReposytory repository, String token, int id, OnCompleteListener<Boolean> onCompleteListener) {
        this.repository = repository;
        this.token = token;
        this.id = id;
        this.onCompleteListener = onCompleteListener;
    }

    public void deleteUser() {
        DeleteUserAsyncTask asyncTask = new DeleteUserAsyncTask(repository, token, id, onCompleteListener);
        asyncTask.execute();
    }

    static class DeleteUserAsyncTask extends AsyncTask<Void, Void, Boolean> {

        final private IUsersReposytory repository;
        final private String token;
        final private int id;
        private OnCompleteListener<Boolean> onCompleteListener;

        DeleteUserAsyncTask(IUsersReposytory repository, String token, int id, OnCompleteListener<Boolean> onCompleteListener) {
            this.repository = repository;
            this.token = token;
            this.id = id;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return repository.deleteUser(token, id);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            onCompleteListener.onComplete(aBoolean);
            super.onPostExecute(aBoolean);
        }
    }
}
