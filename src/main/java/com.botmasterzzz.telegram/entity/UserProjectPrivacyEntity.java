package com.botmasterzzz.telegram.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "project_privacy")
public class UserProjectPrivacyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "messenger_id")
    private Integer messengerID;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private UserEntity userEntity;
    
    @Column(name = "visibility")
    private boolean visibility;
    
    @Column(name = "ip_address")
    private String ipAddress;
    
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getMessengerID() {
        return messengerID;
    }

    public void setMessengerID(Integer messengerID) {
        this.messengerID = messengerID;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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
}
