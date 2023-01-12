package com.kata.Booking.API.domain.repository;

import com.kata.Booking.API.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    List<Reservation> getAll();
    Reservation saveReservation(Reservation reservation);

    Optional<Reservation> getOneById(Long id);

    Optional <Reservation> getOnByReservationNumber(String reservationNumber);

    void deleteOne(Reservation reservation);
}
