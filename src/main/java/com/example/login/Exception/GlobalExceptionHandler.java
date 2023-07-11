package com.example.login.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleException(Exception e){
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code("internal server error")
                .message(e.getMessage()).build();

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler({SignUpFormNotUniqueException.class, SignUpFailedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleSignUpFormNotUniqueException(Exception e){
        final ErrorResponse errorResponse = ErrorResponse.builder()
                .code("bad request")
                .message(e.getMessage()).build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

}
