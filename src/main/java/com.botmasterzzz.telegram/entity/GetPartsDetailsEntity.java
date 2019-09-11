package com.botmasterzzz.telegram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "get_parts_details")
public class GetPartsDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "cat_name")
    private String catName;

    @Column(name = "width")
    private String width;

    @Column(name = "height")
    private String height;

    @Column(name = "length")
    private String length;

    @Column(name = "weight")
    private String weight;

    @Column(name = "colour")
    private String colour;

    @Column(name = "material")
    private String material;

    @JsonIgnore
    @JoinColumn(name = "part_id")
    @OneToOne(cascade = CascadeType.ALL)
    private GetPartsEntity getPartsEntity;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public GetPartsEntity getGetPartsEntity() {
        return getPartsEntity;
    }

    public void setGetPartsEntity(GetPartsEntity getPartsEntity) {
        this.getPartsEntity = getPartsEntity;
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
        return "GetPartsDetailsEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", catName='" + catName + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", length='" + length + '\'' +
                ", weight='" + weight + '\'' +
                ", colour='" + colour + '\'' +
                ", material='" + material + '\'' +
                ", getPartsEntity=" + getPartsEntity +
                ", audWhenCreate=" + audWhenCreate +
                ", audWhenUpdate=" + audWhenUpdate +
                '}';
    }
}
