package com.botmasterzzz.telegram.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "telegram_media_statistic")
public class TelegramRatingStatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "note")
    private String note;

    private long countOfTouch;

    @JoinColumn(name = "telegram_user_id", referencedColumnName = "telegram_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH, targetEntity = TelegramBotUserEntity.class)
    private TelegramBotUserEntity telegramBotUserEntity;

    @Column(name = "touch_type")
    private String touchType;

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

    public String getNote() {
        return note;
    }

    public long getCountOfTouch() {
        return countOfTouch;
    }

    public void setCountOfTouch(long countOfTouch) {
        this.countOfTouch = countOfTouch;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TelegramBotUserEntity getTelegramBotUserEntity() {
        return telegramBotUserEntity;
    }

    public void setTelegramBotUserEntity(TelegramBotUserEntity telegramBotUserEntity) {
        this.telegramBotUserEntity = telegramBotUserEntity;
    }

    public String getTouchType() {
        return touchType;
    }

    public void setTouchType(String touchType) {
        this.touchType = touchType;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramRatingStatisticEntity that = (TelegramRatingStatisticEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(note, that.note) &&
                Objects.equal(telegramBotUserEntity, that.telegramBotUserEntity) &&
                Objects.equal(touchType, that.touchType) &&
                Objects.equal(audWhenCreate, that.audWhenCreate) &&
                Objects.equal(audWhenUpdate, that.audWhenUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, note, telegramBotUserEntity, touchType, audWhenCreate, audWhenUpdate);
    }

    @Override
    public String toString() {
        return "TelegramRatingStatisticEntity{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", telegramBotUserEntity=" + telegramBotUserEntity +
                ", touchType='" + touchType + '\'' +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                '}';
    }
}
