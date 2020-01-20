package com.example.racs.Locks;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocksPOJO {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("next")
    @Expose
    private Object next;
    @SerializedName("previous")
    @Expose
    private Object previous;

    public class Lock {

        @SerializedName("l_id")
        @Expose
        private Integer lId;
        @SerializedName("uuid")
        @Expose
        private String uuid;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("version")
        @Expose
        private String version;
        @SerializedName("is_approved")
        @Expose
        private Boolean isApproved;
        @SerializedName("last_echo")
        @Expose
        private String lastEcho;
        @SerializedName("is_on")
        @Expose
        private Boolean isOn;

        public Integer getLId() {
            return lId;
        }

        public void setLId(Integer lId) {
            this.lId = lId;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Boolean getIsApproved() {
            return isApproved;
        }

        public void setIsApproved(Boolean isApproved) {
            this.isApproved = isApproved;
        }

        public String getLastEcho() {
            return lastEcho;
        }

        public void setLastEcho(String lastEcho) {
            this.lastEcho = lastEcho;
        }

        public Boolean getIsOn() {
            return isOn;
        }

        public void setIsOn(Boolean isOn) {
            this.isOn = isOn;
        }

    }

    @SerializedName("results")
    @Expose
    private ArrayList<Lock> results = new ArrayList<>();

    public ArrayList<Lock> getResults() {
        return results;
    }

    public void setResults(ArrayList<Lock> results) {
        this.results = results;
    }


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



}
