package com.kata.Booking.API.application.resources;

import java.time.LocalDate;

public record RoomAvailabilityResource(Long roomId, LocalDate availableFrom, LocalDate availableTo) { }
