package com.kata.Booking.API.infrastructure.H2.adapter;

import com.kata.Booking.API.domain.Reservation;
import com.kata.Booking.API.domain.repository.ReservationRepository;
import com.kata.Booking.API.infrastructure.H2.jpa.repository.ReservationJpaRepository;
import com.kata.Booking.API.infrastructure.H2.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationAdapter implements ReservationRepository {

    private final ReservationJpaRepository jpaRepository;

    private final ReservationMapper mapper;

    @Override
    public List<Reservation> getAllReservationsByRoomId(Long roomId) {
        return mapper.fromEntities(jpaRepository.findByRoom_Id(roomId));
    }

    @Override
    public void deleteOne(Reservation reservation) {
        jpaRepository.delete(mapper.toEntity(reservation));
    }
}
