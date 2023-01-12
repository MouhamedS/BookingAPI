package com.kata.Booking.API.infrastructure.H2.mapper;

import com.kata.Booking.API.domain.Reservation;
import com.kata.Booking.API.infrastructure.H2.dao.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface ReservationMapper {

    ReservationEntity toEntity(Reservation reservation);

    Reservation fromEntity(ReservationEntity entity);

    List<Reservation> fromEntities(List<ReservationEntity> entities);
}
