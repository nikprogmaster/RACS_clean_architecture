package com.example.racs.domain.usecases.addusecases;

import android.os.AsyncTask;

import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.repository.IAccessesRepository;
import com.example.racs.domain.usecases.OnCompleteListener;

public class AddAccess {

    private final IAccessesRepository repository;
    private final AccessPostEntity body;
    private final String token;
    private OnCompleteListener<Boolean> onCompleteListener;

    public AddAccess(IAccessesRepository repository, AccessPostEntity body, String token, OnCompleteListener<Boolean> onCompleteListener) {
        this.repository = repository;
        this.body = body;
        this.token = token;
        this.onCompleteListener = onCompleteListener;
    }

    public void addUser() {
        AddAccessAsyncTask asyncTask = new AddAccessAsyncTask(repository, body, token, onCompleteListener);
        asyncTask.execute();
    }

    static class AddAccessAsyncTask extends AsyncTask<Void, Void, Boolean> {

        private final IAccessesRepository repository;
        private final AccessPostEntity body;
        private final String token;
        private OnCompleteListener<Boolean> onCompleteListener;

        AddAccessAsyncTask(IAccessesRepository repository, AccessPostEntity body, String token, OnCompleteListener<Boolean> onCompleteListener) {
            this.repository = repository;
            this.body = body;
            this.token = token;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return repository.addAssess(token, body);

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            onCompleteListener.onComplete(aBoolean);
            super.onPostExecute(aBoolean);
        }
    }

}
