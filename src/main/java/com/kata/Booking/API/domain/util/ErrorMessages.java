package com.kata.Booking.API.domain.util;

public class ErrorMessages {

    public static final String INVALID_NAME = "Invalid Name: Name cannot be null";
    public static final String INVALID_TYPE = "Invalid Type: Must be one of the following [DELUXE, LUXURY, SUITE]";

    public static final String INVALID_DATE = "Invalid date: Please input dates in yyyy-MM-dd format";
    public static final String INVALID_DATE_ORDER = "Invalid date: Start date must be before or equals end date";
    public static final String INVALID_RESERVATION_DURATION = "Invalid duration: Reservation is longer than 3 days";

    public static final String INVALID_RESERVATION_TIME= "Invalid time: Reservation cannot be done more than 30 days in advanced";
    public static final String INVALID_RESERVATION_DATES = "Invalid Reservation Dates: The check in and/or check out dates are outside the available dates.";
    public static final String INVALID_DATE_OVERLAP = "Invalid Date: The entered date overlaps with an already existing reservation.";
    public static final String INVALID_ROOM_ID = "Invalid Room: This room id %s does not exist.";

    public static final String INVALID_DATE_ROOM_UNAVAILABLE = "Invalid Date: The entered date are not within the availability window of the room.";

    public static final String INVALID_RESERVATION_NUMBER = "Invalid Reservation: This reservation with number %s does not exist.";
    public static final String INVALID_DATE_NULL_VALUES = "Invalid Date: If a date is entered, both the available to and the available from must be provided.";
}
