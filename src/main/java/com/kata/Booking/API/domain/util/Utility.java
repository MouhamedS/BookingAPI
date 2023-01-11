package com.kata.Booking.API.domain.util;

import com.kata.Booking.API.domain.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.kata.Booking.API.domain.util.ErrorMessages.*;
import static java.time.temporal.ChronoUnit.DAYS;

@Slf4j
public class Utility {

    public static void validateDates(Date from, Date to) {
        //Checks to see if one of the dates are null and throws an exception if only one date has been entered.
        if (from == null || to == null) {
            throw new InvalidRequestException(INVALID_DATE_NULL_VALUES);
        } else {
            //If the dates contain a string value then proceed.

            boolean orderIsValid = from.before(to);
            if (!orderIsValid) {
                throw new InvalidRequestException(INVALID_DATE_ORDER);
            }
        }
    }

    /**
     * Check if the resevation duration does not exceed 3 days
     * @param from the check in date
     * @param to the check out date
     */
    public static void validateReservationDuration(Date from, Date to) {
        if (DAYS.between(from.toInstant(), to.toInstant()) > 3) {
            throw  new InvalidRequestException(INVALID_RESERVATION_DURATION);
        }
    }

    public static void validateReservationTime(Date from) {
        Date now = new Date();
        if (DAYS.between(now.toInstant() , from.toInstant()) > 30) {
            throw  new InvalidRequestException(INVALID_RESERVATION_TIME);
        }
    }


    /**
     * Validator for the date format (yyyy-MM-DD)
     *
     * @param String
     * @return boolean
     */
    private static boolean validateDateFormat(String date) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            log.debug("Invalid date " + date);
            throw new InvalidRequestException(INVALID_DATE);
        }
        return true;
    }
}
