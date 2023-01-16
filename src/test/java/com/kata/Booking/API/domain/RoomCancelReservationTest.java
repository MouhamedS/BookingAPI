package com.kata.Booking.API.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

public class RoomCancelReservationTest {

    private Hotel hotel;

    private Room room;

    @BeforeEach
    void init() {
        room = new Room();
        room.setId(1L);
        room.setName("Room-1");
        room.setHotelId(1L);
        room.setAvailableFrom(LocalDate.of(2023, 01, 01));
        room.setAvailableTo(LocalDate.of(2023,12,31));
        hotel = new Hotel(1L, "CANCUN-GREATEST", Arrays.asList(room));
    }

    @Test
    void cancelReservationTest(){
        room.createReservation(LocalDate.of(2023,01,01), LocalDate.of(2023,01   ,01));

        Assertions.assertThat(room.getReservations().size()).isEqualTo(1);
        room.cancelReservation(room.getReservations().get(0));
        Assertions.assertThat(room.getReservations().size()).isEqualTo(0);
    }
}
