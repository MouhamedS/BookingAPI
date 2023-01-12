package com.kata.Booking.API.infrastructure.H2.dao;

import com.kata.Booking.API.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ROOM")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "ID")
    private  Long id;

    @Column(name = "NAME")
    private  String name;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "AVALAIBLE_FROM")
    private Date availableFrom;
    @Column(name = "AVALAIBLE_TO")
    private Date availableTo;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private HotelEntity hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<ReservationEntity> reservations;
}
