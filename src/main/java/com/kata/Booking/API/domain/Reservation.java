package com.kata.Booking.API.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


public record Reservation (Long roomId, Date dateCheckIn, Date dateCheckOut, String reservationNumber) {}
