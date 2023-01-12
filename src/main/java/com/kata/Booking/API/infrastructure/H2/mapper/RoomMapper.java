package com.kata.Booking.API.infrastructure.H2.mapper;

import com.kata.Booking.API.domain.Room;
import com.kata.Booking.API.infrastructure.H2.dao.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface RoomMapper {

    RoomEntity toEntity(Room room);

    Room fromEntity(RoomEntity entity);
}
