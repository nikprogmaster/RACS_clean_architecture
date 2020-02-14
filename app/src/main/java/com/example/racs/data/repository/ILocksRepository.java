package com.example.racs.data.repository;

import com.example.racs.data.entities.LocksEntity;

import java.util.List;

public interface ILocksRepository {

    List<LocksEntity.Lock> getLocks(String token, int count);

    boolean deleteLock(String token, int id);
}
