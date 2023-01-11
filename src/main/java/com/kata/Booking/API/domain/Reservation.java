package com.kata.Booking.API.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    private Long id;

    private Long roomId;
    private Date dateCheckIn;

    private Date dateCheckOut;

    private String reservationNumber;


}
