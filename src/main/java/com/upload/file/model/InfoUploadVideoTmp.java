package com.upload.file.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.upload.file.enums.StatusVideoFile;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class InfoUploadVideoTmp {

    private String taskId;
    private Path path;
    private List<String> thumblais;
    private StatusVideoFile statusVideoFile;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss Z")
    private Date uploadDate;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public List<String> getThumblais() {
        return thumblais;
    }

    public void setThumblais(List<String> thumblais) {
        this.thumblais = thumblais;
    }

    public StatusVideoFile getStatusVideoFile() {
        return statusVideoFile;
    }

    public void setStatusVideoFile(StatusVideoFile statusVideoFile) {
        this.statusVideoFile = statusVideoFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoUploadVideoTmp that = (InfoUploadVideoTmp) o;
        return Objects.equals(taskId, that.taskId) &&
                Objects.equals(uploadDate, that.uploadDate) &&
                Objects.equals(path, that.path) &&
                Objects.equals(thumblais, that.thumblais) &&
                statusVideoFile == that.statusVideoFile;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, uploadDate, path, thumblais, statusVideoFile);
    }
}
