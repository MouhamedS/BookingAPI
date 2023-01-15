package com.kata.Booking.API.infrastructure.H2.jpa.repository;

import com.kata.Booking.API.infrastructure.H2.dao.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<ReservationEntity, Long> {

    List<ReservationEntity> findByRoom_Id(Long roomId);
    Optional <ReservationEntity> findByReservationNumber(String reservationNumber);
}
