package com.example.racs.domain.usecases.getusecases;

import android.os.AsyncTask;

import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.data.repository.IAccessesRepository;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;

public class GetAccessesToLock {

    private IAccessesRepository repository;
    private Integer lockId;
    private List<AccessEntity.AccPOJO> accesses;
    private List<UsersEntity.User> users;
    private OnCompleteListener<List<UsersEntity.User>> onCompleteListener;

    public GetAccessesToLock(IAccessesRepository repository, Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> users, OnCompleteListener<List<UsersEntity.User>> onCompleteListener) {
        this.repository = repository;
        this.lockId = lockId;
        this.accesses = accesses;
        this.users = users;
        this.onCompleteListener = onCompleteListener;

    }

    public void getAccessesToLock(){
        GetAccessesToLockAsyncTask asyncTask = new GetAccessesToLockAsyncTask(repository, lockId, accesses, users, onCompleteListener);
        asyncTask.execute();
    }

    static class GetAccessesToLockAsyncTask extends AsyncTask<Void, Void, List<UsersEntity.User>> {

        final private IAccessesRepository repository;
        final private Integer lockId;
        final private List<AccessEntity.AccPOJO> accesses;
        final private List<UsersEntity.User> users;
        private OnCompleteListener<List<UsersEntity.User>> onCompleteListener;

        GetAccessesToLockAsyncTask(IAccessesRepository repository, Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> users, OnCompleteListener<List<UsersEntity.User>> onCompleteListener) {
            this.repository = repository;
            this.lockId = lockId;
            this.accesses = accesses;
            this.users = users;
            this.onCompleteListener = onCompleteListener;
        }

        @Override
        protected List<UsersEntity.User> doInBackground(Void... voids) {
            return repository.getAccessesToLock(lockId, accesses, users);
        }

        @Override
        protected void onPostExecute(List<UsersEntity.User> users) {
            onCompleteListener.onComplete(users);
            super.onPostExecute(users);
        }
    }
}