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
     * Method thread-safe to avoid that two user to create a reservation at the same time
     * @param checkIn  Date check in
     * @param checkOut Date check out
     * @param roomId  Id of the room
     * @return  The created reservation
     */
    Reservation createReservation(LocalDate checkIn, LocalDate checkOut, Long roomId);


    /**
     *Modify the reservation
     *
     * @param reservation The reservation to modify
     * @param roomId room identification Number
     * @return modify reservation
     */
    Boolean modifyReservation(Reservation reservation, Long roomId);

    /**
     * Service to cancel an existing reservation
     *
     * @param reservationNumber  Reservation number
     * @param roomId room identification Number
     * @return
     */
    Boolean cancelReservation (String reservationNumber, Long roomId);


    /**
     * Service to find room  by id
     *
     * @param roomId room identification Number
     * @return the Room object
     */
    Optional<Room> getRoomById(Long roomId);
}
