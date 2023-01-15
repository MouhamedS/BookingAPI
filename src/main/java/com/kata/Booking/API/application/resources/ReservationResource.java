package com.kata.Booking.API.application.resources;

import java.time.LocalDate;

public record ReservationResource(Long roomId, LocalDate dateCheckIn, LocalDate dateCheckOut, String reservationNumber) {}
