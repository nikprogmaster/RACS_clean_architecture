package com.example.racs.domain.usecases;

import android.os.AsyncTask;
import android.util.Log;

import com.example.racs.data.entities.AuthEntity;
import com.example.racs.data.entities.AuthPostEntity;
import com.example.racs.data.repository.IAuthRepository;

public class Authorize {

    private IAuthRepository repository;
    private AuthPostEntity body;
    private OnCompleteListener<AuthEntity> onCompleteListener;

    public Authorize(IAuthRepository repository, AuthPostEntity body, OnCompleteListener<AuthEntity> onCompleteListener) {
        this.repository = repository;
        this.body = body;
        this.onCompleteListener = onCompleteListener;
    }

    public void authorize(){
        AuthorizeAsyncTask asyncTask = new AuthorizeAsyncTask(repository, body, onCompleteListener);
        asyncTask.execute();
    }

    static class AuthorizeAsyncTask extends AsyncTask<Void, Void, AuthEntity> {

        private final IAuthRepository repository;
        private final AuthPostEntity body;
        private OnCompleteListener<AuthEntity> onCompleteListener;

        AuthorizeAsyncTask(IAuthRepository repository, AuthPostEntity body, OnCompleteListener<AuthEntity> onCompleteListener) {
            this.repository = repository;
            this.body = body;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        protected AuthEntity doInBackground(Void... voids) {
            return repository.getTokens(body);


        }

        @Override
        protected void onPostExecute(AuthEntity authEntity) {
            onCompleteListener.onComplete(authEntity);
            super.onPostExecute(authEntity);
        }
    }


}
