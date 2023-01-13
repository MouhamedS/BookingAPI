package com.kata.Booking.API.application.resources;

import java.util.Date;

public record ReservationResource(Long roomId, Date dateCheckIn, Date dateCheckOut, String reservationNumber) {}
