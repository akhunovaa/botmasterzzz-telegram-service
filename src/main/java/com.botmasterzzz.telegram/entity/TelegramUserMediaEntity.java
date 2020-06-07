package com.botmasterzzz.telegram.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "telegram_user_media")
public class TelegramUserMediaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "width")
    private Integer width;

    @Column(name = "height")
    private Integer height;

    @Column(name = "file_size")
    private Integer fileSize;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_type")
    private Integer fileType;

    @JoinColumn(name = "telegram_user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private TelegramBotUserEntity telegramBotUserEntity;

//    @Formula(value = "(SELECT count(*) FROM telegram_media_statistic statistic inner join telegram_bot_users usr on statistic.telegram_user_id=usr.telegram_id WHERE statistic.touch_type='HEART' and statistic.telegram_user_id=usr.telegram_id and statistic.media_file = this_.id)")
    @Transient
    private long heartCount;

    @Transient
    private long likeCount;

    @Transient
    private long dislikeCount;

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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
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

    public long getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(long heartCount) {
        this.heartCount = heartCount;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramUserMediaEntity that = (TelegramUserMediaEntity) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(fileId, that.fileId) &&
                Objects.equal(width, that.width) &&
                Objects.equal(height, that.height) &&
                Objects.equal(fileSize, that.fileSize) &&
                Objects.equal(filePath, that.filePath) &&
                Objects.equal(fileType, that.fileType) &&
                Objects.equal(telegramBotUserEntity, that.telegramBotUserEntity) &&
                Objects.equal(audWhenCreate, that.audWhenCreate) &&
                Objects.equal(audWhenUpdate, that.audWhenUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, fileId, width, height, fileSize, filePath, fileType, telegramBotUserEntity, audWhenCreate, audWhenUpdate);
    }

    @Override
    public String toString() {
        return "TelegramUserMediaEntity{" +
                "id=" + id +
                ", fileId='" + fileId + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", fileSize=" + fileSize +
                ", filePath='" + filePath + '\'' +
                ", fileType=" + fileType +
                ", telegramBotUserEntity=" + telegramBotUserEntity +
                ", heartCount=" + heartCount +
                ", likeCount=" + likeCount +
                ", dislikeCount=" + dislikeCount +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                '}';
    }
}
