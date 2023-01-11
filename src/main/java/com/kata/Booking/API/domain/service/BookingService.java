package com.kata.Booking.API.domain.service;

import com.kata.Booking.API.domain.Reservation;

import java.util.Date;

public interface BookingService {

    Reservation createReservation(Date checkIn, Date checkOut);


}
