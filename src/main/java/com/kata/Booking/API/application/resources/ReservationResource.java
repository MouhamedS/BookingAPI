package com.kata.Booking.API.application.resources;

import lombok.Data;

import java.util.Date;

@Data
public class ReservationResource {

    private Long id;

    private Long roomId;
    private Date dateCheckIn;

    private Date dateCheckOut;

    private String reservationNumber;
}
