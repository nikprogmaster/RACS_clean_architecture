package com.example.racs.model.domain;

import java.util.Objects;

public class RefreshEntityDomain {


    private String refresh;

    public RefreshEntityDomain(String refresh){
        this.refresh = refresh;
    }

    public RefreshEntityDomain(){
        super();
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
        RefreshEntityDomain that = (RefreshEntityDomain) o;
        return refresh.equals(that.refresh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refresh);
    }

    @Override
    public String toString() {
        return "RefreshEntityDomain{" +
                "refresh='" + refresh + '\'' +
                '}';
    }

}
