package com.botmasterzzz.telegram.dto;

import java.util.Optional;

public class CallBackData {

    private String path;
    private Integer categoryId;
    private Integer offset;
    private Integer limit;
    private Integer productId;
    private int fileCount;
    private int fileSelected;

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

    public Integer getOffset(int max) {
        return Optional.ofNullable(this.offset).orElse(0) > max ? max : Optional.ofNullable(this.offset).orElse(0);
    }

    public void setOffset(Integer offset) {
        this.offset = offset < 0 ? 0 : offset;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public int getFileSelected() {
        return fileSelected;
    }

    public void setFileSelected(int fileSelected) {
        this.fileSelected = fileSelected;
    }

    @Override
    public String toString() {
        return "CallBackData{" +
                "path='" + path + '\'' +
                ", categoryId=" + categoryId +
                ", offset=" + offset +
                ", limit=" + limit +
                ", productId=" + productId +
                '}';
    }
}
