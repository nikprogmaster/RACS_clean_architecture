package com.example.racs.domain.usecases.deleteusecases;

import android.os.AsyncTask;

import com.example.racs.data.repository.ILocksRepository;
import com.example.racs.domain.usecases.OnCompleteListener;

public class DeleteLock {

    private ILocksRepository repository;
    private String token;
    private int id;
    private OnCompleteListener<Boolean> onCompleteListener;

    public DeleteLock(ILocksRepository repository, String token, int id, OnCompleteListener<Boolean> onCompleteListener) {
        this.repository = repository;
        this.token = token;
        this.id = id;
        this.onCompleteListener = onCompleteListener;
    }

    public void deleteLock() {
        DeleteLockAsyncTask asyncTask = new DeleteLockAsyncTask(repository, token, id, onCompleteListener);
        asyncTask.execute();
    }

    static class DeleteLockAsyncTask extends AsyncTask<Void, Void, Boolean> {

        final private ILocksRepository repository;
        final private String token;
        final private int id;
        private OnCompleteListener<Boolean> onCompleteListener;

        DeleteLockAsyncTask(ILocksRepository repository, String token, int id, OnCompleteListener<Boolean> onCompleteListener) {
            this.repository = repository;
            this.token = token;
            this.id = id;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            return repository.deleteLock(token, id);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            onCompleteListener.onComplete(aBoolean);
            super.onPostExecute(aBoolean);
        }
    }

}
