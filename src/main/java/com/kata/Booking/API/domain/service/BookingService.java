package com.kata.Booking.API.domain.service;

import com.kata.Booking.API.domain.Reservation;

import java.util.Date;
import java.util.List;

public interface BookingService {

    List<Reservation> getAllReservations();
    Reservation createReservation(Date checkIn, Date checkOut, Long roomId);

    Boolean modifyReservation(Reservation reservation, Long roomId);

    Boolean cancelReservation (String reservationNumber, Long roomId);
}
