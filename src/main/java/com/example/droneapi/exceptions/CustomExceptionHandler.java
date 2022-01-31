package com.example.droneapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value ={CustomException.class})
    public ResponseEntity<?> handleDroneLoadExceptions(CustomException customException){
        CustomExceptionPayload customExceptionPayload = new CustomExceptionPayload();
        customExceptionPayload.setMessage(customException.getMessage());
        customExceptionPayload.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(customExceptionPayload, HttpStatus.BAD_REQUEST);
    }
}
