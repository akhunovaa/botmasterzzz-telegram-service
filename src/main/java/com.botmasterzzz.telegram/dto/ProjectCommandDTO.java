package com.botmasterzzz.telegram.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

public class ProjectCommandDTO {

    private long id;

    @NotBlank(message = "Команда не может быть пустой")
    private String command;

    @NotBlank(message = "Название не может быть пустым")
    private String commandName;

    @NotBlank(message = "Ответ не может быть пустым")
    private String answer;

    @NotNull(message = "Проект не может быть пустым")
    private Long projectId;

    @NotNull(message = "Тип проекта не может быть пустым")
    private ProjectCommandTypeDTO commandType;

    private boolean privacy;

    private ProjectCommandDTO parent;

    @JsonIgnore
    private String note;

    @JsonIgnore
    private Integer messengerId;

    @JsonIgnore
    private Long userId;

    @JsonIgnore
    private Timestamp audWhenCreate;

    @JsonIgnore
    private Timestamp audWhenUpdate;

    @JsonIgnore
    private boolean notFound;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public boolean getPrivacy() {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public boolean isPrivacy() {
        return privacy;
    }

    public boolean isNotFound() {
        return notFound;
    }

    public void setNotFound(boolean notFound) {
        this.notFound = notFound;
    }

    public ProjectCommandTypeDTO getCommandType() {
        return commandType;
    }

    public void setCommandType(ProjectCommandTypeDTO projectCommandTypeDTO) {
        this.commandType = projectCommandTypeDTO;
    }

    public ProjectCommandDTO getParent() {
        return parent;
    }

    public void setParent(ProjectCommandDTO parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "ProjectCommandDTO{" +
                "id=" + id +
                ", command='" + command + '\'' +
                ", commandName='" + commandName + '\'' +
                ", answer='" + answer + '\'' +
                ", projectId=" + projectId +
                ", commandType=" + commandType +
                ", privacy=" + privacy +
                ", parent=" + parent +
                ", note='" + note + '\'' +
                ", messengerId=" + messengerId +
                ", userId=" + userId +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                ", notFound=" + notFound +
                '}';
    }
}
