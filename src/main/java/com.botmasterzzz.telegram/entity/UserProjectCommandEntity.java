package com.botmasterzzz.telegram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_project_command")
public class UserProjectCommandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "command")
    private String command;

    @JsonIgnore
    @Column(name = "command_name")
    private String commandName;

    @Column(name = "answer")
    private String answer;

    @JsonIgnore
    @JoinColumn(name = "project_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private UserProjectEntity project;

    @Column(name = "privacy")
    private boolean privacy;

    @JsonIgnore
    @Column(name = "note")
    private String note;

    @Column(name = "messenger_id")
    private Integer messengerId;

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private UserEntity user;

    @JsonIgnore
    @JoinColumn(name = "type_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private UserProjectCommandTypeEntity userProjectCommandTypeEntity;

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

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public UserProjectEntity getProject() {
        return project;
    }

    public void setProject(UserProjectEntity project) {
        this.project = project;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getMessengerId() {
        return messengerId;
    }

    public void setMessengerId(Integer messengerId) {
        this.messengerId = messengerId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserProjectCommandTypeEntity getUserProjectCommandTypeEntity() {
        return userProjectCommandTypeEntity;
    }

    public void setUserProjectCommandTypeEntity(UserProjectCommandTypeEntity userProjectCommandTypeEntity) {
        this.userProjectCommandTypeEntity = userProjectCommandTypeEntity;
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
        return "UserProjectCommandEntity{" +
                "id=" + id +
                ", command='" + command + '\'' +
                ", commandName='" + commandName + '\'' +
                ", answer='" + answer + '\'' +
                ", project=" + project +
                ", privacy=" + privacy +
                ", note='" + note + '\'' +
                ", messengerId=" + messengerId +
                ", user=" + user +
                ", userProjectCommandTypeEntity=" + userProjectCommandTypeEntity +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                '}';
    }
}
