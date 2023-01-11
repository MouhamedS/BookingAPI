package com.kata.Booking.API.domain.repository;

import com.kata.Booking.API.domain.Reservation;

import java.util.List;

public interface ReservationRepository {

    List<Reservation> getAll();
    Reservation saveReservation(Reservation reservation);

    Reservation getOneById(Long Id);

    Reservation getOnByReservationNumber(String reservationNumber);

    Reservation deleteOne(Reservation reservation);
}
