
package com.example.racs.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AccessPostEntityData {

    @SerializedName("a_id")
    @Expose
    private Integer a_id;

    @SerializedName("lock")
    @Expose
    private Integer lock;
    @SerializedName("user")
    @Expose
    private Integer user;
    @SerializedName("access_start")
    @Expose
    private String access_start;
    @SerializedName("access_stop")
    @Expose
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
        AccessPostEntityData that = (AccessPostEntityData) o;
        return Objects.equals(a_id, that.a_id) &&
                lock.equals(that.lock) &&
                user.equals(that.user) &&
                access_start.equals(that.access_start) &&
                access_stop.equals(that.access_stop);
    }

    @Override
    public String toString() {
        return "AccessPostEntityData{" +
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
