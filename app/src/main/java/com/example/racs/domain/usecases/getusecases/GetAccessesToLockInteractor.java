package com.example.racs.domain.usecases.getusecases;

import com.example.racs.model.data.AccessEntityData;
import com.example.racs.model.data.UsersEntityData;
import com.example.racs.data.repository.IAccessesRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class GetAccessesToLockInteractor {

    private IAccessesRepository repository;

    public GetAccessesToLockInteractor(IAccessesRepository repository) {
        this.repository = repository;
    }

    public Single<ArrayList<UsersEntityData.User>> getAccessesToLock(int lockId, List<UsersEntityData.User> users, List<AccessEntityData.Access> accesses) {
        return repository.getAccessesToLock(lockId, accesses, users);
    }


}
