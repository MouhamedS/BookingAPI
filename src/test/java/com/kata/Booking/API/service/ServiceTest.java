package com.kata.Booking.API.service;

import com.kata.Booking.API.domain.Hotel;
import com.kata.Booking.API.domain.Reservation;
import com.kata.Booking.API.domain.Room;
import com.kata.Booking.API.domain.exceptions.InvalidRequestException;
import com.kata.Booking.API.domain.repository.ReservationRepository;
import com.kata.Booking.API.domain.repository.RoomRespository;
import com.kata.Booking.API.domain.service.BookingService;
import com.kata.Booking.API.domain.service.impl.BookingServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private  ReservationRepository reservationRepository;

    @Mock
    private RoomRespository roomRespository;


    private BookingService bookingService;

    private Hotel hotel;

    private Room room;
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        room = new Room();
        room.setId(1L);
        room.setName("Room-1");
        room.setHotelId(1L);
        room.setAvailableFrom(LocalDate.of(2023, 01, 01));
        room.setAvailableTo(LocalDate.of(2023,12,31));
        hotel = new Hotel(1L, "CANCUN-GREATEST", Arrays.asList(room));
        bookingService = new BookingServiceImpl(reservationRepository, roomRespository);
    }

    @Test
    void getAllReservationByRoomIdTest() {
        LocalDate checkIn = LocalDate.of(2023,01,01);
        LocalDate checkOut = LocalDate.of(2023,01   ,01);
        Reservation reservation = new Reservation(1L, checkIn, checkOut, "QDS23");
        List<Reservation> reservations = Arrays.asList(reservation);

        Mockito.when(reservationRepository.getAllReservationsByRoomId(any())).thenReturn(reservations);

        List<Reservation> reservationsByRoomId= bookingService.getAllReservationsByRoomId(1L);

        Assertions.assertThat(reservationsByRoomId.size()).isEqualTo(1);
        Assertions.assertThat(reservationsByRoomId.get(0)).isEqualTo(reservation);

        verify(reservationRepository).getAllReservationsByRoomId(any());

    }
    @Test
    void createReservationPositiveTest(){
        LocalDate checkIn = LocalDate.of(2023,01,01);
        LocalDate checkOut = LocalDate.of(2023,01   ,01);

        Mockito.when(roomRespository.getOneById(any())).thenReturn(Optional.of(room));

        bookingService.createReservation(checkIn, checkOut, 1L);

        Assertions.assertThat(room.getReservations().size()).isEqualTo(1);

        verify(roomRespository).getOneById(any());
    }

    @Test
    void createReservationNegativeTest(){
        LocalDate checkIn = LocalDate.of(2023,01,01);
        LocalDate checkOut = LocalDate.of(2023,01   ,01);

        Mockito.when(roomRespository.getOneById(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThatThrownBy(() -> bookingService.createReservation(checkIn, checkOut, 1L))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid Room: This room id 1 does not exist.");


        verify(roomRespository).getOneById(any());
    }

    @Test
    void modifyReservationPositiveTest(){
        LocalDate checkIn = LocalDate.of(2023,01,01);
        LocalDate checkOut = LocalDate.of(2023,01   ,01);
        Reservation reservation = new Reservation(1L, checkIn, checkOut, "QDS23");
        room.getReservations().add(reservation);

        Reservation reservationModified = new Reservation(1L, LocalDate.of(2023,01, 02), LocalDate.of(2023,01, 03), "QDS23");
        Mockito.when(roomRespository.getOneById(any())).thenReturn(Optional.of(room));

        bookingService.modifyReservation(reservationModified, 1L);

        Assertions.assertThat(room.getReservations().size()).isEqualTo(1);
        Assertions.assertThat(room.getReservations().get(0).dateCheckIn()).isEqualTo(LocalDate.of(2023,01, 02));
        Assertions.assertThat(room.getReservations().get(0).dateCheckOut()).isEqualTo(LocalDate.of(2023,01, 03));

        verify(roomRespository).getOneById(any());
    }
    @Test
    void modifyReservationNegativeTest1(){
        LocalDate checkIn = LocalDate.of(2023,01,01);
        LocalDate checkOut = LocalDate.of(2023,01   ,01);
        Reservation reservation = new Reservation(1L, checkIn, checkOut, "QDS23");
        room.getReservations().add(reservation);

        Reservation reservationModified = new Reservation(1L, LocalDate.of(2023,01, 02), LocalDate.of(2023,01, 03), "QDS23");

        Mockito.when(roomRespository.getOneById(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThatThrownBy(() -> bookingService.modifyReservation(reservationModified, 1L))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid Room: This room id 1 does not exist.");


        verify(roomRespository).getOneById(any());
    }

    @Test
    void modifyReservationNegativeTest2(){
        LocalDate checkIn = LocalDate.of(2023,01,01);
        LocalDate checkOut = LocalDate.of(2023,01   ,01);
        Reservation reservation = new Reservation(1L, checkIn, checkOut, "QDS23");
        room.getReservations().add(reservation);

        Reservation reservationModified = new Reservation(1L, LocalDate.of(2023,01, 02), LocalDate.of(2023,01, 03), "QDS22");

        Mockito.when(roomRespository.getOneById(any())).thenReturn(Optional.ofNullable(room));

        Assertions.assertThatThrownBy(() -> bookingService.modifyReservation(reservationModified, 1L))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid Reservation: This reservation with number QDS22 does not exist.");

        //Assertions.assertThat(room.getReservations().size()).isEqualTo(1);

        verify(roomRespository).getOneById(any());
    }


    @Test
    void cancelReservationPositiveTest(){
        LocalDate checkIn = LocalDate.of(2023,01,01);
        LocalDate checkOut = LocalDate.of(2023,01   ,01);
        Reservation reservation = new Reservation(1L, checkIn, checkOut, "QDS23");
        room.getReservations().add(reservation);

        Reservation reservationModified = new Reservation(1L, LocalDate.of(2023,01, 02), LocalDate.of(2023,01, 03), "QDS23");
        Mockito.when(roomRespository.getOneById(any())).thenReturn(Optional.of(room));

        bookingService.cancelReservation("QDS23", 1L);

        Assertions.assertThat(room.getReservations().size()).isEqualTo(0);
        verify(roomRespository).getOneById(any());
    }

    @Test
    void cancelReservationNegativeTest1(){
        LocalDate checkIn = LocalDate.of(2023,01,01);
        LocalDate checkOut = LocalDate.of(2023,01   ,01);
        Reservation reservation = new Reservation(1L, checkIn, checkOut, "QDS23");
        room.getReservations().add(reservation);

        Mockito.when(roomRespository.getOneById(any())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThatThrownBy(() -> bookingService.cancelReservation("QDS23", 1L))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid Room: This room id 1 does not exist.");


        verify(roomRespository).getOneById(any());
    }

    @Test
    void cancelReservationNegativeTest2(){
        LocalDate checkIn = LocalDate.of(2023,01,01);
        LocalDate checkOut = LocalDate.of(2023,01   ,01);
        Reservation reservation = new Reservation(1L, checkIn, checkOut, "QDS23");
        room.getReservations().add(reservation);

        Mockito.when(roomRespository.getOneById(any())).thenReturn(Optional.ofNullable(room));

        Assertions.assertThatThrownBy(() -> bookingService.cancelReservation("QDS22", 1L))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("Invalid Reservation: This reservation with number QDS22 does not exist.");

        //Assertions.assertThat(room.getReservations().size()).isEqualTo(1);

        verify(roomRespository).getOneById(any());
    }

    @Test
    void getRoomById(){
        Mockito.when(roomRespository.getOneById(any())).thenReturn(Optional.of(room));

        Optional<Room> optionalRoom = bookingService.getRoomById(1L);

        Assertions.assertThat(optionalRoom.isPresent()).isTrue();
        Assertions.assertThat(optionalRoom.get()).isEqualTo(room);
    }
}
