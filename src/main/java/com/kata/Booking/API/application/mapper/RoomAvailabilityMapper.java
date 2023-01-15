package com.kata.Booking.API.application.mapper;

import com.kata.Booking.API.application.resources.RoomAvailabilityResource;
import com.kata.Booking.API.domain.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface RoomAvailabilityMapper {

    @Mapping(source = "id", target = "roomId")
    RoomAvailabilityResource toResource(Room room);
}
