package com.botmasterzzz.telegram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "command_type")
public class UserProjectCommandTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @Column(name = "note")
    private String note;

    @JsonIgnore
    @Column(name = "aud_when_create")
    private Timestamp audWhenCreate;

    @JsonIgnore
    @Column(name = "aud_when_update")
    private Timestamp audWhenUpdate;

    public Long getId() {
        return null == id ? 1 : id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return null == name ? "Текст" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return null == description ? "Ответ содержащий текст" : description;
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
