package com.example.racs.model.domain;

import java.util.Objects;

public class AccessPostEntityDomain {


    private Integer a_id;
    private Integer lock;
    private Integer user;
    private String access_start;
    private String access_stop;

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

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getAccessStart() {
        return access_start;
    }

    public void setAccessStart(String accessStart) {
        this.access_start = accessStart;
    }

    public String getAccessStop() {
        return access_stop;
    }

    public void setAccessStop(String accessStop) {
        this.access_stop = accessStop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessPostEntityDomain that = (AccessPostEntityDomain) o;
        return Objects.equals(a_id, that.a_id) &&
                lock.equals(that.lock) &&
                user.equals(that.user) &&
                access_start.equals(that.access_start) &&
                access_stop.equals(that.access_stop);
    }

    @Override
    public String toString() {
        return "AccessPostEntityDomain{" +
                "a_id=" + a_id +
                ", lock=" + lock +
                ", user=" + user +
                ", access_start='" + access_start + '\'' +
                ", access_stop='" + access_stop + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(a_id, lock, user, access_start, access_stop);
    }

}
