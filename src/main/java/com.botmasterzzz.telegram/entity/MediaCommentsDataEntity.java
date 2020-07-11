package com.botmasterzzz.telegram.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "media_comments_data")
public class MediaCommentsDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "data")
    private String data;

    @JoinColumn(name = "media_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private TelegramUserMediaEntity telegramUserMediaEntity;

    @JoinColumn(name = "telegram_user_id", referencedColumnName = "telegram_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, targetEntity = TelegramBotUserEntity.class)
    private TelegramBotUserEntity telegramBotUserEntity;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public TelegramUserMediaEntity getTelegramUserMediaEntity() {
        return telegramUserMediaEntity;
    }

    public void setTelegramUserMediaEntity(TelegramUserMediaEntity telegramUserMediaEntity) {
        this.telegramUserMediaEntity = telegramUserMediaEntity;
    }

    public TelegramBotUserEntity getTelegramBotUserEntity() {
        return telegramBotUserEntity;
    }

    public void setTelegramBotUserEntity(TelegramBotUserEntity telegramBotUserEntity) {
        this.telegramBotUserEntity = telegramBotUserEntity;
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
        return "MediaCommentsDataEntity{" +
                "id=" + id +
                ", data='" + data + '\'' +
                ", telegramUserMediaEntity=" + telegramUserMediaEntity +
                ", telegramBotUserEntity=" + telegramBotUserEntity +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaCommentsDataEntity that = (MediaCommentsDataEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(data, that.data) &&
                Objects.equal(telegramUserMediaEntity, that.telegramUserMediaEntity) &&
                Objects.equal(telegramBotUserEntity, that.telegramBotUserEntity) &&
                Objects.equal(audWhenCreate, that.audWhenCreate) &&
                Objects.equal(audWhenUpdate, that.audWhenUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, data, telegramUserMediaEntity, telegramBotUserEntity, audWhenCreate, audWhenUpdate);
    }
}
