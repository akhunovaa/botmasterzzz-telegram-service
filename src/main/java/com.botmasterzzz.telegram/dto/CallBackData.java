package com.botmasterzzz.telegram.dto;

import java.util.Optional;

public class CallBackData {

    private String path;
    private Integer categoryId;
    private String ca;
    private Integer offset;
    private Integer limit;
    private Integer productId;
    private Integer fileCount;
    private Integer fileSelected;

    public CallBackData() {
    }

    public CallBackData(String path) {
        this.path = path;
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

    @Override
    public String toString() {
        return "CallBackData{" +
                "path='" + path + '\'' +
                ", categoryId=" + categoryId +
                ", ca='" + ca + '\'' +
                ", offset=" + offset +
                ", limit=" + limit +
                ", productId=" + productId +
                ", fileCount=" + fileCount +
                ", fileSelected=" + fileSelected +
                '}';
    }
}
