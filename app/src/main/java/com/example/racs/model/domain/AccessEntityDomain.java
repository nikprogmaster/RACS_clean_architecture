package com.example.racs.model.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Single;

public class AccessEntityDomain {


    private Integer a_id;
    private Integer lock;
    private String lockDesc;
    private Integer user;
    private String cardId;
    private String accessStart;
    private String accessStop;

    public Integer getA_id() {
        return a_id;
    }

    public void setA_id(Integer a_id) {
        this.a_id = a_id;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public String getLockDesc() {
        return lockDesc;
    }

    public void setLockDesc(String lockDesc) {
        this.lockDesc = lockDesc;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getAccessStart() {
        return accessStart;
    }

    public void setAccessStart(String accessStart) {
        this.accessStart = accessStart;
    }

    public String getAccessStop() {
        return accessStop;
    }

    public void setAccessStop(String accessStop) {
        this.accessStop = accessStop;
    }


   /* public static Single<ArrayList<UsersEntityDomain>> searchUsersByLock(Integer lockId, List<AccessEntityDomain> accesses, List<UsersEntityDomain> allusers) {
        UsersEntityDomain users = new UsersEntityDomain();
        for (AccessEntityDomain i : accesses) {
            if (i.lock == lockId) {
                UsersEntityDomain user = users.searchById(i.user, allusers);
                if (user != null) {
                    users.getUsers().add(user);
                }
            }
        }
        return Single.fromObservable(Observable.fromArray(users.getUsers()));
    }

    public static String searchUserById(List<UsersEntityDomain> allusers, int user_id) {
        for (UsersEntityDomain user : allusers) {
            if (user.getUId() == user_id) {
                return user.getFirstName() + " " + user.getLastName() + " " + user.getPatronymic();
            }
        }
        return null;
    }

    public static String searchLockById(List<LocksEntityDomain.Lock> locks, int lock_id) {
        for (LocksEntityDomain.Lock lock : locks) {
            if (lock.getLId() == lock_id) {
                return lock.getDescription();
            }
        }
        return null;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessEntityDomain that = (AccessEntityDomain) o;
        return Objects.equals(a_id, that.a_id) &&
                Objects.equals(lock, that.lock) &&
                Objects.equals(lockDesc, that.lockDesc) &&
                Objects.equals(user, that.user) &&
                Objects.equals(cardId, that.cardId) &&
                Objects.equals(accessStart, that.accessStart) &&
                Objects.equals(accessStop, that.accessStop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a_id, lock, lockDesc, user, cardId, accessStart, accessStop);
    }

    @Override
    public String toString() {
        return "AccessEntityDomain{" +
                "a_id=" + a_id +
                ", lock=" + lock +
                ", lockDesc='" + lockDesc + '\'' +
                ", user=" + user +
                ", cardId='" + cardId + '\'' +
                ", accessStart='" + accessStart + '\'' +
                ", accessStop='" + accessStop + '\'' +
                '}';
    }
}