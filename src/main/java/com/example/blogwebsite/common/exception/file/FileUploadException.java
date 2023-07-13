package com.example.blogwebsite.common.exception.file;

public class FileUploadException extends SpringBootFileUploadException {

    public FileUploadException(String message) {
        super(message);
    }
}