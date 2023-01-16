package com.kata.Booking.API.application.resources;

import java.time.LocalDate;

public record ReservationCreateResource(LocalDate dateCheckIn, LocalDate dateCheckOut) {
}
