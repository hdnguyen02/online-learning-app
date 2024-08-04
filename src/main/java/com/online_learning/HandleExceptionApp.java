package com.online_learning;

import com.online_learning.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class HandleExceptionApp {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e) {
        Response responseObject = Response.builder()
                .success(false)
                .message(e.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
    }
}

