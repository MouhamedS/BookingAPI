package com.kata.Booking.API.application.mapper;

import com.kata.Booking.API.application.resources.ReservationResource;
import com.kata.Booking.API.domain.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface ReservationResourceMapper {

    ReservationResource toResource(Reservation reservation);

    List<ReservationResource> toResources(List<Reservation> reservations);

    Reservation fromResource(ReservationResource reservationResource);
}
