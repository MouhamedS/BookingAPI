package com.kata.Booking.API.domain.repository;

import com.kata.Booking.API.domain.Room;

import java.util.Optional;

public interface RoomRespository {

    /**
     *  Find  a room givent the Id
     * @param id
     * @return
     */
    Optional<Room> getOneById(Long id);

    /**
     * Save room into the database
     *
     * @param room
     * @return
     */
    Room saveRoom(Room room);
}
