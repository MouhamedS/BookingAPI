package com.kata.Booking.API.infrastructure.H2.adapter;

import com.kata.Booking.API.domain.Room;
import com.kata.Booking.API.domain.repository.RoomRespository;
import com.kata.Booking.API.infrastructure.H2.jpa.repository.RoomJpaRepository;
import com.kata.Booking.API.infrastructure.H2.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoomAdpater implements RoomRespository {

    private final RoomJpaRepository jpaRepository;

    private final RoomMapper mapper;

    @Override
    public Optional<Room> getOneById(Long id) {
        return Optional.ofNullable(mapper.fromEntity(jpaRepository.findById(id).orElse(null)));
    }

    @Override
    public Room saveRoom(Room room) {
        return mapper.fromEntity(jpaRepository.save(mapper.toEntity(room)));
    }
}
