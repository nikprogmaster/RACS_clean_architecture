package com.example.racs.model.domain;

import java.util.Objects;

public class LocksEntityDomain {

    private Integer lId;
    private String uuid;
    private String description;
    private String version;
    private Boolean isApproved;
    private String lastEcho;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocksEntityDomain that = (LocksEntityDomain) o;
        return Objects.equals(lId, that.lId) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(description, that.description) &&
                Objects.equals(version, that.version) &&
                Objects.equals(isApproved, that.isApproved) &&
                Objects.equals(lastEcho, that.lastEcho) &&
                Objects.equals(isOn, that.isOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lId, uuid, description, version, isApproved, lastEcho, isOn);
    }

    @Override
    public String toString() {
        return "LocksEntityDomain{" +
                "lId=" + lId +
                ", uuid='" + uuid + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", isApproved=" + isApproved +
                ", lastEcho='" + lastEcho + '\'' +
                ", isOn=" + isOn +
                '}';
    }
}
