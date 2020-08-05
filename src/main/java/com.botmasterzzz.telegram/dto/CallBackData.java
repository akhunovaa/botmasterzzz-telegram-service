package com.botmasterzzz.telegram.dto;

import java.util.Optional;

public class CallBackData {

    private String path;
    private Long userId;
    private Integer categoryId;
    private String ca;
    private Integer offset;
    private Integer limit;
    private Integer l;
    private Integer o;
    private Integer productId;
    private Integer fileCount;
    private Integer fileSelected;
    private Long fileId;

    public CallBackData() {
    }

    public CallBackData(String path) {
        this.path = path;
    }

    public CallBackData(String path, Long fileId) {
        this.path = path;
        this.fileId = fileId;
    }

    public CallBackData(String path, Long userId, Long fileId) {
        this.path = path;
        this.fileId = fileId;
        this.userId = userId;
    }

    public CallBackData(Long telegramId) {
        this.userId = telegramId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset(Integer max) {
        return Optional.ofNullable(this.offset).orElse(0) > max ? max : Optional.ofNullable(this.offset).orElse(0);
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset < 0 ? 0 : offset;
    }

    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
    }

    public Integer getFileSelected() {
        return fileSelected;
    }

    public void setFileSelected(Integer fileSelected) {
        this.fileSelected = fileSelected;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Integer getL() {
        return l;
    }

    public void setL(Integer l) {
        this.l = l;
    }

    public Integer getO() {
        return o;
    }

    public void setO(Integer o) {
        this.o = o;
    }

    @Override
    public String toString() {
        return "CallBackData{" +
                "path='" + path + '\'' +
                ", userId=" + userId +
                ", categoryId=" + categoryId +
                ", ca='" + ca + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                ", l=" + l +
                ", o=" + o +
                ", productId=" + productId +
                ", fileCount=" + fileCount +
                ", fileSelected=" + fileSelected +
                ", fileId=" + fileId +
                '}';
    }
}
