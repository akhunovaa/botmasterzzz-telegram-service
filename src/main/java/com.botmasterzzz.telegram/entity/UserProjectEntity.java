package com.botmasterzzz.telegram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_project")
public class UserProjectEntity {

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

    @Column(name = "image_url")
    private String imageUrl;

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private UserEntity userEntity;

    @JsonIgnore
    @Column(name = "token")
    private String token;

    @JsonIgnore
    @Column(name = "secret")
    private String secret;

    @Column(name = "is_private")
    private boolean isPrivate;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    @Override
    public String toString() {
        return "UserProjectEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", userEntity=" + userEntity +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                '}';
    }
}
