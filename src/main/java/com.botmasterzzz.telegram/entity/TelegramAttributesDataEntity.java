package com.botmasterzzz.telegram.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "telegram_attributes_data")
public class TelegramAttributesDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @JoinColumn(name = "telegram_user_id", referencedColumnName = "telegram_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, targetEntity = TelegramBotUserEntity.class)
    private TelegramBotUserEntity telegramBotUserEntity;

    @JoinColumn(name = "bot_instance")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private TelegramInstanceEntity telegramInstanceEntity;

    @Column(name = "aud_when_create")
    private Timestamp audWhenCreate;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TelegramBotUserEntity getTelegramBotUserEntity() {
        return telegramBotUserEntity;
    }

    public void setTelegramBotUserEntity(TelegramBotUserEntity telegramBotUserEntity) {
        this.telegramBotUserEntity = telegramBotUserEntity;
    }

    public TelegramInstanceEntity getTelegramInstanceEntity() {
        return telegramInstanceEntity;
    }

    public void setTelegramInstanceEntity(TelegramInstanceEntity telegramInstanceEntity) {
        this.telegramInstanceEntity = telegramInstanceEntity;
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
        return "TelegramAttributesDataEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", name='" + name + '\'' +
                ", telegramBotUserEntity=" + telegramBotUserEntity +
                ", telegramInstanceEntity=" + telegramInstanceEntity +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                '}';
    }
}
