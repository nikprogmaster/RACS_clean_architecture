package com.example.racs.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class RefreshEntityData {

    @SerializedName("refresh")
    @Expose
    private String refresh;

    public RefreshEntityData(String refresh){
        this.refresh = refresh;
    }

    public RefreshEntityData(){
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
        RefreshEntityData that = (RefreshEntityData) o;
        return refresh.equals(that.refresh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refresh);
    }

    @Override
    public String toString() {
        return "RefreshEntityData{" +
                "refresh='" + refresh + '\'' +
                '}';
    }
}
