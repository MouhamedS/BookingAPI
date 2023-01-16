package com.kata.Booking.API.domain;


import com.kata.Booking.API.domain.exceptions.InvalidRequestException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class RoomTest {

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
    void testCreateReservationOneDay (){
        room.createReservation(LocalDate.of(2023,01,01), LocalDate.of(2023,01   ,01));
        Assertions.assertThat(room.getReservations().size()).isEqualTo(1);
    }

    @Test
    void testCreateReservationTwoDay (){
        room.createReservation(LocalDate.of(2023,01,01), LocalDate.of(2023,01   ,02));
        Assertions.assertThat(room.getReservations().size()).isEqualTo(1);
    }

    @Test
    void testCreateReservation3Day (){
        room.createReservation(LocalDate.of(2023,01,01), LocalDate.of(2023,01   ,03));
        Assertions.assertThat(room.getReservations().size()).isEqualTo(1);
    }

    @Test
    void testCreateReservation4Day (){

        Assertions.assertThatThrownBy(() -> room.createReservation(LocalDate.of(2023,01,01), LocalDate.of(2023,01   ,04)))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid duration: ReservationMapper is longer than 3 days");
    }

    @Test
    void testCreateReservationMoreThan30DaysPrior() {
        Assertions.assertThatThrownBy(() -> room.createReservation(LocalDate.of(2023,10,01), LocalDate.of(2023,10   ,03)))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid time: ReservationMapper cannot be done more than 30 days in advanced");
    }

    @Test
    void testCreateOverlappingReservation() {
        room.createReservation(LocalDate.of(2023,01,01), LocalDate.of(2023,01   ,03));
        Assertions.assertThatThrownBy(() -> room.createReservation(LocalDate.of(2023,01,02), LocalDate.of(2023,01   ,04)))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid Date: The entered date overlaps with an already existing reservation.");
    }

    @Test
    void testCreateReservationWithAvailableDate() {
        room.createReservation(LocalDate.of(2023,01,06), LocalDate.of(2023,01   ,07));
        Assertions.assertThatThrownBy(() -> room.createReservation(LocalDate.of(2023,01,02), LocalDate.of(2023,01   ,04)))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid Date: The entered date are not within the availability window of the room.");
    }




}
