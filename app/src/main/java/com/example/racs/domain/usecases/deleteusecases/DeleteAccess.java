package com.example.racs.domain.usecases.deleteusecases;

import android.os.AsyncTask;

import com.example.racs.data.repository.IAccessesRepository;
import com.example.racs.domain.usecases.OnCompleteListener;

public class DeleteAccess {

    private IAccessesRepository repository;
    private String token;
    private int id;
    private OnCompleteListener<Boolean> onCompleteListener;

    public DeleteAccess(IAccessesRepository repository, String token, int id, OnCompleteListener<Boolean> onCompleteListener) {
        this.repository = repository;
        this.token = token;
        this.id = id;
        this.onCompleteListener = onCompleteListener;
    }

    public void deleteUser() {
        DeleteAccessAsyncTask asyncTask = new DeleteAccessAsyncTask(repository, token, id, onCompleteListener);
        asyncTask.execute();
    }

    static class DeleteAccessAsyncTask extends AsyncTask<Void, Void, Boolean> {

        final private IAccessesRepository repository;
        final private String token;
        final private int id;
        private OnCompleteListener<Boolean> onCompleteListener;

        DeleteAccessAsyncTask(IAccessesRepository repository, String token, int id, OnCompleteListener<Boolean> onCompleteListener) {
            this.repository = repository;
            this.token = token;
            this.id = id;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return repository.deleteAccess(token, id);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            onCompleteListener.onComplete(aBoolean);
            super.onPostExecute(aBoolean);
        }
    }

}
