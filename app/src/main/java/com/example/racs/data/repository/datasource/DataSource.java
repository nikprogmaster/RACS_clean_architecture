package com.example.racs.data.repository.datasource;

import com.example.racs.data.entities.AccessEntity;
import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.entities.AuthEntity;
import com.example.racs.data.entities.AuthPostEntity;
import com.example.racs.data.entities.LocksEntity;
import com.example.racs.data.entities.RefreshEntity;
import com.example.racs.data.entities.UserPostEntity;
import com.example.racs.data.entities.UsersEntity;

import java.util.List;

public interface DataSource {

    List<LocksEntity.Lock> locksList(String token, int count);

    List<UsersEntity.User> usersList(String token, int count);

    List<AccessEntity.AccPOJO> accessesList(String token, int count);

    List<UsersEntity.User> getAccessesToLock(Integer lockId, List<AccessEntity.AccPOJO> accesses, List<UsersEntity.User> allusers);

    boolean addAccess(String token, AccessPostEntity body);

    boolean addUser(String token, UserPostEntity body);

    boolean deleteAccess(String token, int id);

    boolean deleteUser(String token, int id);

    boolean deleteLock(String token, int id);

    AuthEntity getTokens(AuthPostEntity body);

}
