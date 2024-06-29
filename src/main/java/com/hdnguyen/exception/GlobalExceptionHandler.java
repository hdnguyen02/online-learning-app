package com.hdnguyen.exception;


import com.hdnguyen.dto.Response;
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
        Response responseData = new Response();
        responseData.setData(false);
        responseData.setSuccess(false);
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }
}
