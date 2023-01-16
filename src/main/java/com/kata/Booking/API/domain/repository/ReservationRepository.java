package com.kata.Booking.API.domain.repository;

import com.kata.Booking.API.domain.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    /**
     * Get all the reservation for a room
     *
     * @param roomId room identification number
     * @return List of reservations for the room
     */
    List<Reservation> getAllReservationsByRoomId(Long roomId);

    /**
     * Delete a reservation
     *
     * @param reservation the reservation
     */
    void deleteOne(Reservation reservation);
}
