package com.kata.Booking.API.domain.service;

import com.kata.Booking.API.domain.Reservation;
import com.kata.Booking.API.domain.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    /**
     * Service to get all the reservations
     *
     * @return List of the reservation for the room
     */
    List<Reservation> getAllReservationsByRoomId(Long roomId);

    /**
     * Service to create a new reservation
     *
     * @param checkIn  Date check in
     * @param checkOut Date check out
     * @param roomId  Id of the room
     * @return  The created reservation
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
     * Service to cancel an existing reservation
     *
     * @param reservationNumber
     * @param roomId
     * @return
     */
    Boolean cancelReservation (String reservationNumber, Long roomId);


    /**
     * Service to find room  by id
     *
     * @param roomId Id of the romm
     * @return the Room object
     */
    Optional<Room> getRoomById(Long roomId);
}
