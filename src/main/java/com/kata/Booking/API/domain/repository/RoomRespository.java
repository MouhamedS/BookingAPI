package com.kata.Booking.API.domain.repository;

import com.kata.Booking.API.domain.Room;

import java.util.Optional;

public interface RoomRespository {

    Optional<Room> getOneById(Long id);

    Room saveRoom(Room room);
}
