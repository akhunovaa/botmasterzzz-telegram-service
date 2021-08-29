package com.botmasterzzz.telegram.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "telegram_media_statistic")
public class TelegramMediaStatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "note")
    private String note;

    @JoinColumn(name = "media_file")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private TelegramUserMediaEntity telegramUserMediaEntity;

    @Column(name = "telegram_user_id")
    private Long telegramUserId;

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

    public void setNote(String note) {
        this.note = note;
    }

    public TelegramUserMediaEntity getTelegramUserMediaEntity() {
        return telegramUserMediaEntity;
    }

    public void setTelegramUserMediaEntity(TelegramUserMediaEntity telegramUserMediaEntity) {
        this.telegramUserMediaEntity = telegramUserMediaEntity;
    }

    public Long getTelegramUserId() {
        return telegramUserId;
    }

    public void setTelegramUserId(Long telegramUserId) {
        this.telegramUserId = telegramUserId;
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
    public String toString() {
        return "TelegramMediaStatisticEntity{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", telegramUserMediaEntity=" + telegramUserMediaEntity +
                ", telegramUserId=" + telegramUserId +
                ", touchType='" + touchType + '\'' +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                '}';
    }
}
