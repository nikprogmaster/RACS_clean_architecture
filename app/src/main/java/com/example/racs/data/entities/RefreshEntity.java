package com.example.racs.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefreshEntity {

    @SerializedName("refresh")
    @Expose
    private String refresh;

    public RefreshEntity(String refresh){
        this.refresh = refresh;
    }

    public RefreshEntity(){
        super();
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}
