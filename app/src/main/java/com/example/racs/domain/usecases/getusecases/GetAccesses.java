package com.example.racs.domain.usecases.getusecases;

import android.os.AsyncTask;

import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.repository.IAccessesRepository;
import com.example.racs.domain.usecases.Authorize;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;

public class GetAccesses {

    private IAccessesRepository repository;
    private String token;
    private int count;
    private OnCompleteListener<List<AccessEntity.AccPOJO>> onCompleteListener;

    public GetAccesses(IAccessesRepository repository, String token, int count, OnCompleteListener<List<AccessEntity.AccPOJO>> onCompleteListener) {
        this.repository = repository;
        this.token = token;
        this.count = count;
        this.onCompleteListener = onCompleteListener;
    }

    public void getAccesses() {
        GetAccessesAsyncTask asyncTask = new GetAccessesAsyncTask(repository, token, count, onCompleteListener);
        asyncTask.execute();
    }

    static class GetAccessesAsyncTask extends AsyncTask<Void, Void, List<AccessEntity.AccPOJO>> {

        private final IAccessesRepository repository;
        private final String token;
        private final int count;
        private OnCompleteListener<List<AccessEntity.AccPOJO>> onCompleteListener;

        GetAccessesAsyncTask(IAccessesRepository repository, String token, int count, OnCompleteListener<List<AccessEntity.AccPOJO>> onCompleteListener) {
            this.repository = repository;
            this.token = token;
            this.count = count;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        protected List<AccessEntity.AccPOJO> doInBackground(Void... voids) {
            return repository.getAccesses(token, count);
        }

        @Override
        protected void onPostExecute(List<AccessEntity.AccPOJO> accPOJOS) {
            onCompleteListener.onComplete(accPOJOS);
            super.onPostExecute(accPOJOS);
        }
    }

}
