package com.botmasterzzz.telegram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bot_usage_statistics")
public class TelegramBotUsageStatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "messenger_id")
    private int messengerId;

    @JoinColumn(name = "bot_instance_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private TelegramInstanceEntity telegramInstanceEntity;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private TelegramBotUserEntity telegramBotUserEntity;

    @Column(name = "note")
    private String note;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(int messengerId) {
        this.messengerId = messengerId;
    }

    public TelegramInstanceEntity getTelegramInstanceEntity() {
        return telegramInstanceEntity;
    }

    public void setTelegramInstanceEntity(TelegramInstanceEntity telegramInstanceEntity) {
        this.telegramInstanceEntity = telegramInstanceEntity;
    }

    public TelegramBotUserEntity getTelegramBotUserEntity() {
        return telegramBotUserEntity;
    }

    public void setTelegramBotUserEntity(TelegramBotUserEntity telegramBotUserEntity) {
        this.telegramBotUserEntity = telegramBotUserEntity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    @Override
    public String toString() {
        return "TelegramBotUsageStatisticEntity{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", messengerId=" + messengerId +
                ", telegramInstanceEntity=" + telegramInstanceEntity +
                ", telegramBotUserEntity=" + telegramBotUserEntity +
                ", note='" + note + '\'' +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                '}';
    }
}