package com.example.blogwebsite.common.exception;


import com.example.blogwebsite.common.model.ResponseDTO;
import com.example.blogwebsite.common.util.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleConstraintViolationException(ConstraintViolationException exception) {
        return ResponseUtil.error(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        return ResponseUtil.error(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BWBusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> handleLoginException(
            BWBusinessException exception
    ) {
        return ResponseUtil.error(exception, HttpStatus.BAD_REQUEST);
    }
}
