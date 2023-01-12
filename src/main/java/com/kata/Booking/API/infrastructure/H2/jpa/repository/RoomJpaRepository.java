package com.kata.Booking.API.infrastructure.H2.jpa.repository;

import com.kata.Booking.API.infrastructure.H2.dao.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomJpaRepository extends JpaRepository<RoomEntity, Long> {
}
