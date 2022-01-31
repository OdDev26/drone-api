package com.example.droneapi.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
public class CustomExceptionPayload {
    private String message;
    private HttpStatus httpStatus;

    public CustomExceptionPayload(String message,HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus= httpStatus;
    }
}
