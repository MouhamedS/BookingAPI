package com.kata.Booking.API.domain;

import java.util.List;


public record  Hotel(Integer id, String name, List<Room> rooms) {}
