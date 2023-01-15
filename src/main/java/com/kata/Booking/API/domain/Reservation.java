package com.kata.Booking.API.domain;

import java.time.LocalDate;


public record Reservation (Long roomId, LocalDate dateCheckIn, LocalDate dateCheckOut, String reservationNumber) {}
