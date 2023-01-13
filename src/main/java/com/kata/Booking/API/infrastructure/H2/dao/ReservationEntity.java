package com.kata.Booking.API.infrastructure.H2.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RESERVATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID")
    private RoomEntity room;

    @Column(name = "DATE_CHECK_IN")
    private Date dateCheckIn;

    @Column(name = "DATE_CHECK_OUT")
    private Date dateCheckOut;

    @Column(name = "RESERVATION_NUMBER")
    private String reservationNumber;
}
