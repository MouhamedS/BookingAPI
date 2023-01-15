package com.kata.Booking.API.domain.service;

import com.kata.Booking.API.domain.Reservation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface BookingService {

    /**
     *
     * @return
     */
    List<Reservation> getAllReservations();

    /**
     *
     * @param checkIn
     * @param checkOut
     * @param roomId
     * @return
     */
    Reservation createReservation(LocalDate checkIn, LocalDate checkOut, Long roomId);


    /**
     *
     * @param reservation
     * @param roomId
     * @return
     */
    Boolean modifyReservation(Reservation reservation, Long roomId);

    /**
     *
     * @param reservationNumber
     * @param roomId
     * @return
     */
    Boolean cancelReservation (String reservationNumber, Long roomId);
}
