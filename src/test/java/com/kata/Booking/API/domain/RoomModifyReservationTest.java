package com.kata.Booking.API.domain;


import com.kata.Booking.API.domain.exceptions.InvalidRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

public class RoomModifyReservationTest {

    private Hotel hotel;

    private Room room;

    @BeforeEach
    void init(){
        room = new Room();
        room.setId(1L);
        room.setName("Room-1");
        room.setHotelId(1L);
        room.setAvailableFrom(LocalDate.of(2023, 01, 01));
        room.setAvailableTo(LocalDate.of(2023,12,31));
        hotel = new Hotel(1L, "CANCUN-GREATEST", Arrays.asList(room));
    }

    @Test
    void testModifyReservationOneDayTest (){
        room.createReservation(LocalDate.of(2023,01,01), LocalDate.of(2023,01   ,01));

        Reservation reservation = room.getReservations().get(0);
        Reservation reservation1 = new Reservation(reservation.roomId(), LocalDate.of(2023,01,02), LocalDate.of(2023,01   ,02), reservation.reservationNumber());
        Assertions.assertThat(room.modifyReservation(reservation1)).isTrue();
    }

    @Test
    void testModifyReservationTwoDayTest (){
        room.createReservation(LocalDate.of(2023,01,01), LocalDate.of(2023,01   ,02));

        Reservation reservation = room.getReservations().get(0);
        Reservation reservation1 = new Reservation(reservation.roomId(), LocalDate.of(2023,01,02), LocalDate.of(2023,01   ,03), reservation.reservationNumber());
        Assertions.assertThat(room.modifyReservation(reservation1)).isTrue();
    }

    @Test
    void testModifyReservation4DayReservationTest (){
        room.createReservation(LocalDate.of(2023,01,01), LocalDate.of(2023,01   ,02));

        Reservation reservation = room.getReservations().get(0);
        Reservation reservation1 = new Reservation(reservation.roomId(), LocalDate.of(2023,01,01), LocalDate.of(2023,01   ,04), reservation.reservationNumber());
        Assertions.assertThatThrownBy(() -> room.modifyReservation(reservation1))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid duration: Reservation is longer than 3 days");
    }

    @Test
    void testModifyOverlappingReservationTest() {
        room.createReservation(LocalDate.of(2023,01,01), LocalDate.of(2023,01   ,03));
        room.createReservation(LocalDate.of(2023,01,31), LocalDate.of(2023,02   ,01));
        Reservation reservation = room.getReservations().get(0);
        Reservation reservation1 = new Reservation(reservation.roomId(), LocalDate.of(2023,02,01), LocalDate.of(2023,02   ,02), reservation.reservationNumber());


        Assertions.assertThatThrownBy(() -> room.modifyReservation(reservation1))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid Date: The entered date overlaps with an already existing reservation.");
    }


    @Test
    void testModifyReservationAt30PriorTest() {
        room.createReservation(LocalDate.of(2023,01,01), LocalDate.of(2023,01   ,01));
        Reservation reservation = room.getReservations().get(0);
        Reservation reservation1 = new Reservation(reservation.roomId(), LocalDate.of(2023,03,01), LocalDate.of(2023,03   ,02), reservation.reservationNumber());

        Assertions.assertThatThrownBy(() -> room.modifyReservation(reservation1))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid time: Reservation cannot be done more than 30 days in advanced");
    }
}
