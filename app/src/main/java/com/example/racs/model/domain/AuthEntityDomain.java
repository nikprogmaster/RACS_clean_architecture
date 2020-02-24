package com.example.racs.model.domain;

import java.util.Objects;

public class AuthEntityDomain {


    private String access;
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
        AuthEntityDomain that = (AuthEntityDomain) o;
        return Objects.equals(access, that.access) &&
                Objects.equals(refresh, that.refresh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access, refresh);
    }

    @Override
    public String toString() {
        return "AuthEntityDomain{" +
                "access='" + access + '\'' +
                ", refresh='" + refresh + '\'' +
                '}';
    }

}
