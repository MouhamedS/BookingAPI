package com.kata.Booking.API.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public record  Hotel(Integer id, String name, List<Room> rooms) {}
