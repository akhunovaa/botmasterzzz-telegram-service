package com.botmasterzzz.telegram.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class ProjectDTO {

    private long id;

    @JsonIgnore
    private long userId;

    @NotBlank(message = "Наименование не может быть пустым")
    @NotNull(message = "Наименование не может быть пустым")
    @Size(min=3, max = 255)
    private String name;

    @Size(max = 1000)
    private String description;

    @Size(max = 1000)
    private String note;

    @Size(max = 1000)
    private String imageUrl;

    @Size(max = 1000)
    private String token;

    @Size(max = 1000)
    private String secret;

    private int userCount;

    private boolean isPrivate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public boolean isIsPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", note='" + note + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", token='" + token + '\'' +
                ", secret='" + secret + '\'' +
                ", userCount=" + userCount +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
