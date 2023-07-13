package com.example.blogwebsite.common.exception.file;

public class FileEmptyException extends SpringBootFileUploadException {
    public FileEmptyException(String message) {
        super(message);
    }
}