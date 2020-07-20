package com.botmasterzzz.telegram.dto;

import java.io.File;

public class ReceivedMediaFile {

    private String title;
    private String description;
    private File file;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "ReceivedMediaFile{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", file=" + file +
                '}';
    }
}
