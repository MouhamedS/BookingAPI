package com.kata.Booking.API.domain;

import com.kata.Booking.API.domain.exceptions.InvalidRequestException;
import com.kata.Booking.API.domain.util.Utility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
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

    private Long hotelId;
    private  String name;
    private String type;
    private LocalDate availableTo;

    private LocalDate availableFrom;

    private BookingStatus status;

    private List<Reservation> reservations = new ArrayList<>();

    /**
     * Create a reservation for this room
     * @param checkIn  check in date
     * @param checkOut check out date
     * @return ReservationMapper
     */
    public Reservation createReservation(LocalDate checkIn, LocalDate checkOut){
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

        Reservation reservation = new Reservation(id, checkIn, checkOut, RandomStringUtils.randomAlphanumeric(5));
        reservations.add(reservation);

        //The availability date must also change

        return reservation;
    }

    /**
     * C
     * @param reservationNumber
     * @return
     */
    public boolean cancelReservation(String reservationNumber){
       Optional<Reservation> optionalReservation = this.getRoomReservationByReservationNumber(reservationNumber);

       return optionalReservation.isPresent();
    }

    /**
     *  Modif
     * @param reservation
     * @return
     */
    public boolean modifyReservation(Reservation reservation){
        //Validate the date for the new reservation
        Utility.validateDates(reservation.dateCheckIn(), reservation.dateCheckOut());

        // Check the stay does not last more than 3 days
        Utility.validateReservationDuration(reservation.dateCheckIn(), reservation.dateCheckOut());

        // Check if the reservation is not done 30 days in advance
        Utility.validateReservationTime(reservation.dateCheckIn());

        // Check if the new reservation does not overlap with existing one
        if (this.reservationOverlaps(reservation.dateCheckIn(), reservation.dateCheckOut())) {
            throw new InvalidRequestException(INVALID_DATE_OVERLAP);
        };
        Optional<Reservation> reservationOptional = this.getRoomReservationByReservationNumber(reservation.reservationNumber());
        if (reservationOptional.isPresent()) {
            this.reservations.remove(reservationOptional.get());
            this.reservations.add(reservation);
            return true;
        }

        return false;
    }

    /**
     * Check if reservation does overlap with another reservation
     * @param checkIn
     * @param checkOut
     * @return
     */
    public boolean reservationOverlaps (LocalDate checkIn, LocalDate checkOut) {

        return this.reservations.stream()
                .anyMatch(reservation -> reservation.dateCheckIn().compareTo(checkOut) == 0
                        || reservation.dateCheckOut().compareTo(checkIn) == 0);

    }

    public Optional <Reservation> getRoomReservationByReservationNumber(String reservationNumber) {
        return this.reservations.stream()
                .filter(reservation -> reservation.reservationNumber().equals(reservationNumber))
                .findFirst();
    }
}
