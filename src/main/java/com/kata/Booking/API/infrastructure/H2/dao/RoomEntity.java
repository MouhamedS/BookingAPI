package com.kata.Booking.API.infrastructure.H2.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(name = "AVAILABLE_FROM")
    private LocalDate availableFrom;
    @Column(name = "AVAILABLE_TO")
    private LocalDate availableTo;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private HotelEntity hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<ReservationEntity> reservations;
}
