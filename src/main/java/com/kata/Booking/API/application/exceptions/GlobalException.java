package com.kata.Booking.API.application.exceptions;

import com.kata.Booking.API.domain.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler( DefaultControllerException.class)
    public ResponseEntity<DefaultErrorMessage> handleDefaultError(DefaultControllerException e) {

        log.info(e.getDefaultErrorMessage().getMessage());
        return ResponseEntity.status(e.getDefaultErrorMessage().getStatus()).body(e.getDefaultErrorMessage());
    }

    @ExceptionHandler( {InvalidRequestException.class})
    public ResponseEntity<String> handleThresholdViolation(InvalidRequestException e) {

        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}
