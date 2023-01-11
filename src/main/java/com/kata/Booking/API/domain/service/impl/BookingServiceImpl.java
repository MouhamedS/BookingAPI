package com.kata.Booking.API.domain.service.impl;

import com.kata.Booking.API.domain.Reservation;
import com.kata.Booking.API.domain.Room;
import com.kata.Booking.API.domain.exceptions.InvalidRequestException;
import com.kata.Booking.API.domain.repository.ReservationRepository;
import com.kata.Booking.API.domain.repository.RoomRespository;
import com.kata.Booking.API.domain.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.kata.Booking.API.domain.util.ErrorMessages.INVALID_RESERVATION_NUMBER;
import static com.kata.Booking.API.domain.util.ErrorMessages.INVALID_ROOM_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl  implements BookingService {

    private final ReservationRepository reservationRepository;

    private final RoomRespository roomRespository;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.getAll();
    }

    @Override
    @Transactional
    public Reservation createReservation(Date checkIn, Date checkOut, Long roomId) {
        log.info("Service createReservation call with parameters checkIn {} checkOut {} roomId {}", checkIn, roomId);
        Optional<Room> optionalRoom = roomRespository.getOneById(roomId);

        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();

            Reservation reservation = room.createReservation(checkIn, checkOut);

             log.info("Room saved into DB {}", roomRespository.saveRoom(room));
             log.info("Reservation created {}", reservation);

             return reservation;
        }

        log.error("Invalid Room: This room id {} does not exist." , roomId);
        throw  new InvalidRequestException(String.format(INVALID_ROOM_ID, roomId));
    }

    @Override
    @Transactional
    public Boolean modifyReservation(Reservation reservation, Long roomId) {
        log.info("Service modifyReservation call with parameters roomId {}", roomId);
        Optional<Room> optionalRoom = roomRespository.getOneById(roomId);

        if (optionalRoom.isPresent()){
            Room room = optionalRoom.get();

            Optional<Reservation> existingReservation = room.getRoomReservationByReservationNumber(reservation.getReservationNumber());
            if (existingReservation.isPresent()) {
                boolean result =  room.modifyReservation(reservation);
                roomRespository.saveRoom(room);

                return result;
            } else {
                log.error("Invalid Reservation: This reservation with number {] does not exist.", reservation.getReservationNumber());
                throw  new InvalidRequestException(String.format(INVALID_RESERVATION_NUMBER, reservation.getReservationNumber()));
            }
        }

        log.error("Invalid Room: This room id {} does not exist." , roomId);
        throw  new InvalidRequestException(String.format(INVALID_ROOM_ID, roomId));
    }

    @Override
    @Transactional
    public Boolean cancelReservation(String reservationNumber, Long roomId) {
        log.info("Service cancelReservation call with parameters {] reservationNumber roomId {}", reservationNumber, roomId);
        Optional<Room> optionalRoom = roomRespository.getOneById(roomId);

        if (optionalRoom.isPresent()){
            Room room = optionalRoom.get();

            Optional<Reservation> existingReservation = room.getRoomReservationByReservationNumber(reservationNumber);

            if (existingReservation.isPresent()) {

                boolean result =  room.cancelReservation(reservationNumber);
                roomRespository.saveRoom(room);
                reservationRepository.deleteOne(existingReservation.get());

                return result;
            }else {
                log.error("Invalid Reservation: This reservation with number {] does not exist.", reservationNumber);
                throw  new InvalidRequestException(String.format(INVALID_RESERVATION_NUMBER, reservationNumber));
            }
        }

        log.error("Invalid Room: This room id {} does not exist." , roomId);
        throw  new InvalidRequestException(String.format(INVALID_ROOM_ID, roomId));
    }
}
