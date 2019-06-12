package com.botmasterzzz.telegram.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.Date;

public class TelegramDTO {

    @JsonIgnore
    private long id;

    @JsonIgnore
    private Integer messengerId;

    @JsonIgnore
    private Long userId;

    private boolean status;

    private String lastError;

    private Long projectId;

    private Timestamp created = new Timestamp(new Date().getTime());

    private Timestamp updated = new Timestamp(new Date().getTime());

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Integer messengerId) {
        this.messengerId = messengerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLastError() {
        return lastError;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
