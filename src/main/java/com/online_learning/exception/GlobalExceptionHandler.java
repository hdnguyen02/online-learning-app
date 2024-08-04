package com.online_learning.exception;


import com.online_learning.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GroupAlreadyExistsException.class)
    public ResponseEntity<Response> handlerGroupAlreadyExistsException(GroupAlreadyExistsException exception,
                                                                       WebRequest webRequest) {
        Response response = new Response();
        response.setData(false);
        response.setSuccess(false);
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
