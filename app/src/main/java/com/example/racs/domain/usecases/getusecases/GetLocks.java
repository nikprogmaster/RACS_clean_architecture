package com.example.racs.domain.usecases.getusecases;

import android.os.AsyncTask;

import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.repository.ILocksRepository;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;

public class GetLocks {

    private ILocksRepository repository;
    private String token;
    private int count;
    private OnCompleteListener<List<LocksEntity.Lock>> onCompleteListener;

    public GetLocks(ILocksRepository repository, String token, int count, OnCompleteListener<List<LocksEntity.Lock>> onCompleteListener) {
        this.repository = repository;
        this.token = token;
        this.count = count;
        this.onCompleteListener = onCompleteListener;
    }

    public void getLocks() {
        GetLocksAsyncTask asyncTask = new GetLocksAsyncTask(repository, token, count, onCompleteListener);
        asyncTask.execute();
    }

    static class GetLocksAsyncTask extends AsyncTask<Void, Void, List<LocksEntity.Lock>> {

        private final ILocksRepository repository;
        private final String token;
        private final int count;
        private OnCompleteListener<List<LocksEntity.Lock>> onCompleteListener;

        GetLocksAsyncTask(ILocksRepository repository, String token, int count, OnCompleteListener<List<LocksEntity.Lock>> onCompleteListener) {
            this.repository = repository;
            this.token = token;
            this.count = count;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        protected List<LocksEntity.Lock> doInBackground(Void... voids) {
            return repository.getLocks(token, count);
        }

        @Override
        protected void onPostExecute(List<LocksEntity.Lock> locks) {
            onCompleteListener.onComplete(locks);
            super.onPostExecute(locks);
        }
    }

}
