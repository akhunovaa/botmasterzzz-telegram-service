package com.botmasterzzz.telegram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "get_parts")
public class GetPartsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "article")
    private String article;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "visibility")
    private boolean visibility;

    @JsonIgnore
    @Column(name = "aud_when_create")
    private Timestamp audWhenCreate;

    @JsonIgnore
    @Column(name = "aud_when_update")
    private Timestamp audWhenUpdate;

    @OneToOne(mappedBy = "getPartsEntity", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private GetPartsDetailsEntity getPartsDetailsEntity;

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

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
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

    public void setGetPartsDetailsEntity(GetPartsDetailsEntity getPartsDetailsEntity) {
        if (getPartsDetailsEntity == null) {
            if (this.getPartsDetailsEntity != null) {
                this.getPartsDetailsEntity.setGetPartsEntity(null);
            }
        }
        else {
            getPartsDetailsEntity.setGetPartsEntity(this);
        }
        this.getPartsDetailsEntity = getPartsDetailsEntity;
    }

    public GetPartsDetailsEntity getGetPartsDetailsEntity() {
        return getPartsDetailsEntity;
    }

    @Override
    public String toString() {
        return "GetPartsEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", article='" + article + '\'' +
                ", brandName='" + brandName + '\'' +
                ", visibility=" + visibility +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                '}';
    }
}
