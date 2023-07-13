package com.example.blogwebsite.common.exception.file;

public class FileDownloadException extends SpringBootFileUploadException {
    public FileDownloadException(String message) {
        super(message);
    }
}