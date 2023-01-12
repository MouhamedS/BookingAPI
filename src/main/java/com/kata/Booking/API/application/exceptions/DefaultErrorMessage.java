package com.kata.Booking.API.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultErrorMessage {

    private int  code;

    private HttpStatus status;

    private String message;
}
