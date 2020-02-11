
package com.example.racs.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AccessEntity {


    public class AccPOJO {

        @SerializedName("a_id")
        @Expose
        private Integer a_id;
        @SerializedName("lock")
        @Expose
        private Integer lock;
        @SerializedName("lock_desc")
        @Expose
        private String lockDesc;
        @SerializedName("user")
        @Expose
        private Integer user;
        @SerializedName("card_id")
        @Expose
        private String cardId;
        @SerializedName("access_start")
        @Expose
        private String accessStart;
        @SerializedName("access_stop")
        @Expose
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


    }


    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private Object next;
    @SerializedName("previous")
    @Expose
    private Object previous;
    @SerializedName("results")
    @Expose
    private ArrayList<AccPOJO> results = new ArrayList<>();

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public ArrayList<AccPOJO> getResults() {
        return results;
    }

    public void setResults(ArrayList<AccPOJO> results) {
        this.results = results;
    }

    public List<UsersEntity.User> searchUsersByLock(Integer lockId, List<AccPOJO> accesses, List<UsersEntity.User> allusers) {
        UsersEntity users = new UsersEntity();
        for (AccPOJO i : accesses) {
            if (i.lock == lockId) {
                UsersEntity.User user = users.searchById(i.user, allusers);
                if (user != null) {
                    users.getUsers().add(user);
                }
            }
        }
        return users.getUsers();
    }

    public static String searchUserById(List<UsersEntity.User> allusers, int user_id) {
        for (UsersEntity.User user : allusers) {
            if (user.getUId() == user_id) {
                return user.getFirstName() + " " + user.getLastName() + " " + user.getPatronymic();
            }
        }
        return null;
    }

    public static String searchLockById(List<LocksEntity.Lock> locks, int lock_id) {
        for (LocksEntity.Lock lock : locks) {
            if (lock.getLId() == lock_id) {
                return lock.getDescription();
            }
        }
        return null;
    }
}
