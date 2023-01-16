package com.kata.Booking.API.domain;

import com.kata.Booking.API.domain.exceptions.InvalidRequestException;
import com.kata.Booking.API.domain.util.Utility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kata.Booking.API.domain.util.ErrorMessages.INVALID_DATE_OVERLAP;
import static com.kata.Booking.API.domain.util.ErrorMessages.INVALID_DATE_ROOM_UNAVAILABLE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private  Long id;

    private Long hotelId;
    private  String name;
    private String type;

    private LocalDate availableFrom;
    private LocalDate availableTo;

    private BookingStatus status;

    private List<Reservation> reservations = new ArrayList<>();

    /**
     * Create a reservation for this room
     *
     *  1. Validate the date for the new reservation
     *  2. Check the stay does not last more than 3 days
     *  3. Check if the reservation is not done 30 days in advance
     *  4. Check if the new reservation does not overlap with existing one
     *  5. Check the date are within the availability window of the room
     *  6. If check are cleared create the reservation and change the room availability date
     *
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
        // Check the date are within the availability window of the room
        if(!this.isAvailable(checkIn, checkOut)){
            throw new InvalidRequestException(INVALID_DATE_ROOM_UNAVAILABLE);
        }

        // Create the reservation if it clears all the checkpoints
        Reservation reservation = new Reservation(id, checkIn, checkOut, RandomStringUtils.randomAlphanumeric(5));
        reservations.add(reservation);

        //Change room availability date // only works if the first booking begin at the first day available else this rule is false
        this.setAvailableFrom(reservation.dateCheckOut().plusDays(1));

        return reservation;
    }

    /**
     * Cancel an existing reservation by removing it from the room existing reservation
     *  1. Set the initial availability date
     *  2. Delete it from the list of reservations
     * @param reservation Reservation to remove
     * @return True if the reservation has been removed else false
     */
    public boolean cancelReservation(Reservation reservation){
        this.setAvailableFrom(reservation.dateCheckIn());
        return this.reservations.remove(reservation);
    }

    /**
     *  Modify an existing reservation
     *  1. Validate the date for the new reservation
     *  2. Check the stay does not last more than 3 days
     *  3. Check if the reservation is not done 30 days in advance
     *  4. Check if the new reservation does not overlap with existing one
     *  5. Check the date are within the availability window of the room
     *  6. If check are cleared create the reservation and change the room availability date
     *
     * @param reservation
     * @return True if the reservation has been modified alse false
     */
    public boolean modifyReservation(Reservation reservation){
        //Validate the date for the new reservation
        Utility.validateDates(reservation.dateCheckIn(), reservation.dateCheckOut());

        // Check the stay does not last more than 3 days
        Utility.validateReservationDuration(reservation.dateCheckIn(), reservation.dateCheckOut());

        // Check if the reservation is not done 30 days in advance
        Utility.validateReservationTime(reservation.dateCheckIn());


        Optional<Reservation> reservationToRemove = this.getRoomReservationByReservationNumber(reservation.reservationNumber());
        if (reservationToRemove.isPresent()) {

            // Check if the new reservation does not overlap with existing one
            if (this.reservationOverlapsForModify(reservationToRemove.get(), reservation.dateCheckIn(), reservation.dateCheckOut())) {
                throw new InvalidRequestException(INVALID_DATE_OVERLAP);
            }

            // Check the date are within the availability window of the room
            if(!this.isAvailableForModify(reservationToRemove.get(), reservation.dateCheckIn(), reservation.dateCheckOut())){
                throw new InvalidRequestException(INVALID_DATE_ROOM_UNAVAILABLE);
            }

            this.reservations.remove(reservationToRemove.get());
            this.reservations.add(reservation);
            //Change room availability date // only works if the first booking begin at the first day available else this rule is false
            this.setAvailableFrom(reservationToRemove.get().dateCheckIn());
            this.setAvailableFrom(reservation.dateCheckOut().plusDays(1));
            return true;
        }

        return false;
    }

    /**
     * Check if reservation does overlap with another reservation
     *  Check if the given date are equals to an existing reservation dates
     *  or the check in date is between an existing reservation date
     * @param checkIn date check in
     * @param checkOut date check out
     * @return
     */
    public boolean reservationOverlaps (LocalDate checkIn, LocalDate checkOut) {

        return this.reservations.stream()
                .anyMatch(reservation -> reservation.dateCheckIn().isEqual(checkOut)
                        || reservation.dateCheckOut().isEqual(checkIn)
                        || reservation.dateCheckIn().isEqual(checkIn)
                        || reservation.dateCheckOut().isEqual(checkOut)
                        || (reservation.dateCheckIn().isBefore(checkIn)
                        && reservation.dateCheckOut().isAfter(checkIn)));

    }


    /**
     *  Check if reservation does overlap with another reservation while modifying an existing reservation
     *  Check if the given date are equals to an existing reservation dates
     *  or the check in date is between an existing reservation date
     * @param checkIn date check in
     * @param checkOut date check out
     * @return
     */
    public boolean reservationOverlapsForModify (Reservation reservationToModify, LocalDate checkIn, LocalDate checkOut) {

        return this.reservations.stream()
                .filter(reservation -> !reservation.reservationNumber().equals(reservationToModify.reservationNumber()))
                .anyMatch(reservation -> reservation.dateCheckIn().isEqual(checkOut)
                        || reservation.dateCheckOut().isEqual(checkIn)
                        || reservation.dateCheckIn().isEqual(checkIn)
                        || reservation.dateCheckOut().isEqual(checkOut)
                        || (reservation.dateCheckIn().isBefore(checkIn)
                        && reservation.dateCheckOut().isAfter(checkIn)));

    }

    /**
     * Get a reservation by the reservation number
     *
     * @param reservationNumber reservation number
     * @return the reservation
     */
    public Optional <Reservation> getRoomReservationByReservationNumber(String reservationNumber) {
        return this.reservations.stream()
                .filter(reservation -> reservation.reservationNumber().equals(reservationNumber))
                .findFirst();
    }

    /**
     * Check if the reservation dates are with the availability windows of the room
     *
     * Check if the given date are between the availability date of the room (both room availability dates are inclusives)
     * @param checkIn Date Check in
     * @param checkOut Date Check Out
     * @return
     */
    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut){
        return (this.availableFrom.isEqual(checkIn) || this.availableFrom.isBefore(checkIn)) &&
                (this.availableTo.isEqual(checkOut) || this.availableTo.isAfter(checkOut));
    }


    /**
     * Check if the reservation dates are with the availability windows of the room while modifying an existing reservation
     *
     * Check if the given date are between the availability date of the room (both room availability dates are inclusives)
     * @param checkIn Date Check in
     * @param checkOut Date Check Out
     * @return
     */
    public boolean isAvailableForModify(Reservation reservationToRemove, LocalDate checkIn, LocalDate checkOut){
        LocalDate availableFromBeforeReservation = reservationToRemove.dateCheckIn();
        return (availableFromBeforeReservation.isEqual(checkIn) || availableFromBeforeReservation.isBefore(checkIn)) &&
                (this.availableTo.isEqual(checkOut) || this.availableTo.isAfter(checkOut));
    }
}
