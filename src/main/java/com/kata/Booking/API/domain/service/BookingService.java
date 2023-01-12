package com.kata.Booking.API.domain.service;

import com.kata.Booking.API.domain.Reservation;

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
    Reservation createReservation(Date checkIn, Date checkOut, Long roomId);

    /**
     *
     * @param reservation
     * @return
     */
    Boolean modifyReservation(Reservation reservation);

    /**
     *
     * @param reservationNumber
     * @param roomId
     * @return
     */
    Boolean cancelReservation (String reservationNumber, Long roomId);
}
