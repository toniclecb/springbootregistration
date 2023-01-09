package com.toniclecb.springbootregistration;

import java.io.Serializable;

public class UploadFileResponseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fileName;
    private String fileUri;
    private String fileType;
    private long fileSize;

    public UploadFileResponseVO(){}

    public UploadFileResponseVO(String fileName, String fileUri, String fileType, long fileSize) {
        this.fileName = fileName;
        this.fileUri = fileUri;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileUri() {
        return fileUri;
    }
    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }
    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public long getFileSize() {
        return fileSize;
    }
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    
}
