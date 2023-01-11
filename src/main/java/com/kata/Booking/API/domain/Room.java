package com.kata.Booking.API.domain;

import com.kata.Booking.API.domain.exceptions.InvalidRequestException;
import com.kata.Booking.API.domain.util.Utility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kata.Booking.API.domain.util.ErrorMessages.INVALID_DATE_OVERLAP;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private  Long id;

    private  String name;

    private String type;
    private Date availableTo;

    private Date availableFrom;

    private BookingStatus status;

    private List<Reservation> reservations = new ArrayList<>();

    public Reservation createReservation(Date checkIn, Date checkOut){
        //Validate the date for the new reservation
        Utility.validateDates(checkIn, checkOut);

        // Check the stay does not last more than 3 days
        Utility.validateReservationDuration(checkIn, checkOut);

        // Check if the reservation is not done 30 days in advance
        Utility.validateReservationTime(checkIn);

        // Check if the new reservation does not overlap with existing one
        if (this.reservationOverlaps(checkIn, checkOut)) {
            throw new InvalidRequestException(INVALID_DATE_OVERLAP);
        };

        Reservation reservation = Reservation.builder()
                .dateCheckIn(checkIn)
                .dateCheckOut(checkOut)
                .reservationNumber(RandomStringUtils.randomAlphanumeric(5))
                .build();
        reservations.add(reservation);

        return reservation;
    }

    public boolean cancelReservation(String reservationNumber){
       Optional<Reservation> reservation = this.reservations.stream()
                .filter(reservation1 -> reservation1.getReservationNumber().equals(reservationNumber))
                .findFirst();

       return reservation.isPresent();
    }

    public boolean modifyReservation(Reservation reservation){
        //Validate the date for the new reservation
        Utility.validateDates(reservation.getDateCheckIn(), reservation.getDateCheckOut());

        // Check the stay does not last more than 3 days
        Utility.validateReservationDuration(reservation.getDateCheckIn(), reservation.getDateCheckOut());

        // Check if the reservation is not done 30 days in advance
        Utility.validateReservationTime(reservation.getDateCheckIn());

        // Check if the new reservation does not overlap with existing one
        if (this.reservationOverlaps(reservation.getDateCheckIn(), reservation.getDateCheckOut())) {
            throw new InvalidRequestException(INVALID_DATE_OVERLAP);
        };
        Optional<Reservation> reservationOptional = this.reservations.stream()
                .filter(reservation1 -> reservation1.equals(reservation))
                .findFirst();
        if (reservationOptional.isPresent()) {
            this.reservations.remove(reservationOptional.get());
            this.reservations.add(reservation);
            return true;
        }

        return false;
    }

    public boolean reservationOverlaps (Date checkIn, Date checkOut) {

        return this.reservations.stream()
                .anyMatch(reservation -> reservation.getDateCheckIn().compareTo(checkOut) == 0
                        || reservation.getDateCheckOut().compareTo(checkIn) == 0);

    }
}