package com.kata.Booking.API.infrastructure.H2.mapper;

import com.kata.Booking.API.domain.Reservation;
import com.kata.Booking.API.domain.Room;
import com.kata.Booking.API.infrastructure.H2.dao.ReservationEntity;
import com.kata.Booking.API.infrastructure.H2.dao.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface RoomMapper {

    @Mapping(source = "hotelId", target = "hotel.id")
    RoomEntity toEntity(Room room);

    @Mapping(source = "hotel.id", target = "hotelId")
    Room fromEntity(RoomEntity entity);


    @Mapping(source = "roomId", target = "room.id")
    ReservationEntity toReservationEntity(Reservation reservation);

    @Mapping(source = "room.id", target = "roomId")
    Reservation fromReservationEntity(ReservationEntity entity);
}
