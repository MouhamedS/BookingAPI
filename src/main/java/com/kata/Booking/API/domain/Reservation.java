package com.kata.Booking.API.domain;

import java.util.Date;


public record Reservation (Long roomId, Date dateCheckIn, Date dateCheckOut, String reservationNumber) {}
