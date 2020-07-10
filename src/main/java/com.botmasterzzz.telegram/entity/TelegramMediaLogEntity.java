package com.botmasterzzz.telegram.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "telegram_media_log")
public class TelegramMediaLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "note")
    private String note;

    @JoinColumn(name = "media_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private TelegramUserMediaEntity telegramUserMediaEntity;

    @Column(name = "telegram_user_id")
    private Long telegramUserId;

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
        return "TelegramMediaLogEntity{" +
                "id=" + id +
                ", note='" + note + '\'' +
                ", telegramUserMediaEntity=" + telegramUserMediaEntity +
                ", telegramUserId=" + telegramUserId +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramMediaLogEntity that = (TelegramMediaLogEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(note, that.note) &&
                Objects.equal(telegramUserMediaEntity, that.telegramUserMediaEntity) &&
                Objects.equal(telegramUserId, that.telegramUserId) &&
                Objects.equal(audWhenCreate, that.audWhenCreate) &&
                Objects.equal(audWhenUpdate, that.audWhenUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, note, telegramUserMediaEntity, telegramUserId, audWhenCreate, audWhenUpdate);
    }
}
