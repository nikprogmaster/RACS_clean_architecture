package com.example.racs.data.repository;

import com.example.racs.data.entities.LocksEntity;
import com.example.racs.domain.usecases.OnCompleteListener;

import java.util.List;

public interface ILocksRepository {

    void getLocks(String token, int count, OnCompleteListener<List<LocksEntity.Lock>> onCompleteListener);

    void deleteLock(String token, int id, OnCompleteListener<Boolean> onCompleteListener);
}
