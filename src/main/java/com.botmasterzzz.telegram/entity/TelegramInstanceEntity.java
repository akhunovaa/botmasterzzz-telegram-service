package com.botmasterzzz.telegram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "bot_instance")
public class TelegramInstanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Column(name = "description")
    private String description;

    @Column(name = "note")
    private String note;

    @Column(name = "messenger_id")
    private Integer messengerId;

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private UserEntity user;

    @JsonIgnore
    @JoinColumn(name = "project_id")
    @OneToOne(cascade = CascadeType.REFRESH)
    private UserProjectEntity project;

    @JsonIgnore
    @OneToMany(
            mappedBy = "telegramInstanceEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TelegramBotUsageStatisticEntity> telegramBotUsageStatisticEntityList;

    @Column(name = "status")
    private boolean status;

    @Column(name = "last_error")
    private String lastError;

    @JsonIgnore
    @Column(name = "aud_when_create")
    private Timestamp audWhenCreate;

    @JsonIgnore
    @Column(name = "aud_when_update")
    private Timestamp audWhenUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Integer messengerId) {
        this.messengerId = messengerId;
    }

    public UserEntity getUserEntity() {
        return user;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.user = userEntity;
    }

    public UserProjectEntity getProject() {
        return project;
    }

    public void setProject(UserProjectEntity project) {
        this.project = project;
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

    public Timestamp getAudWhenCreate() {
        return audWhenCreate;
    }

    public void setAudWhenCreate(Timestamp audWhenCreate) {
        this.audWhenCreate = audWhenCreate;
    }

    public Timestamp getAudWhenUpdate() {
        return audWhenUpdate;
    }

    public void setAudWhenUpdate(Timestamp audWhenUpdate) {
        this.audWhenUpdate = audWhenUpdate;
    }

    public List<TelegramBotUsageStatisticEntity> getTelegramBotUsageStatisticEntityList() {
        return telegramBotUsageStatisticEntityList;
    }

    public void setTelegramBotUsageStatisticEntityList(List<TelegramBotUsageStatisticEntity> telegramBotUsageStatisticEntityList) {
        this.telegramBotUsageStatisticEntityList = telegramBotUsageStatisticEntityList;
    }

    @Override
    public String toString() {
        return "TelegramInstanceEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", messengerId=" + messengerId +
                ", userEntity=" + user +
                ", project=" + project +
                ", status=" + status +
                ", lastError='" + lastError + '\'' +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                '}';
    }
}
