package com.example.racs.Locks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LockPostPOJO {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("version")
    @Expose
    private Integer version;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
