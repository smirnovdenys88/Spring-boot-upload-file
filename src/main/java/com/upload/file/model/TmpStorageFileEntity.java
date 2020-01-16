package com.upload.file.model;


public class TmpStorageFileEntity {

    private Long id;
    private Long channelId;
    private String fileName;
    private String contentType;
    private short typeFile;

    public TmpStorageFileEntity() {
    }

    public TmpStorageFileEntity(Long channelId, String fileName, short typeFile, String contentType) {
        this.channelId = channelId;
        this.fileName = fileName;
        this.typeFile = typeFile;
        this.contentType = contentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public short getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(short typeFile) {
        this.typeFile = typeFile;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
