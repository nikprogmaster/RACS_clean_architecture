package com.example.racs.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class AuthEntityData {

    @SerializedName("access")
    @Expose
    private String access;
    @SerializedName("refresh")
    @Expose
    private String refresh;

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthEntityData that = (AuthEntityData) o;
        return Objects.equals(access, that.access) &&
                Objects.equals(refresh, that.refresh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access, refresh);
    }

    @Override
    public String toString() {
        return "AuthEntityData{" +
                "access='" + access + '\'' +
                ", refresh='" + refresh + '\'' +
                '}';
    }
}
