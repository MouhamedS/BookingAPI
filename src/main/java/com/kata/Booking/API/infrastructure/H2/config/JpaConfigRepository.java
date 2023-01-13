package com.kata.Booking.API.infrastructure.H2.config;

import com.kata.Booking.API.infrastructure.H2.jpa.repository.ReservationJpaRepository;
import com.kata.Booking.API.infrastructure.H2.jpa.repository.RoomJpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = {ReservationJpaRepository.class, RoomJpaRepository.class})
public class JpaConfigRepository {
}
