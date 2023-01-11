package com.kata.Booking.API.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    private Integer id;

    private String name;

    private List<Room> rooms;
}
