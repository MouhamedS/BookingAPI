package com.kata.Booking.API.application.controllers;

import com.kata.Booking.API.application.exceptions.DefaultControllerException;
import com.kata.Booking.API.application.exceptions.DefaultErrorMessage;
import com.kata.Booking.API.application.mapper.RoomAvailabilityMapper;
import com.kata.Booking.API.application.resources.RoomAvailabilityResource;
import com.kata.Booking.API.domain.service.BookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name ="Reservation API", description = "API to do reservation operations")
@Slf4j
@RestController(value = "RoomController")
@RequestMapping(value = "/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final BookingService bookingService;
    private final RoomAvailabilityMapper roomAvailabilityMapper;

    @GetMapping(value = "/{roomId}/availability", produces = "application/json" )
    public RoomAvailabilityResource getRoomAvailability(@PathVariable("roomId")Long roomId){
        log.info("Call endpoint GET /api/v1/reservations to retrieve all reservations");
        return bookingService
                .getRoomById(roomId)
                .map(room -> roomAvailabilityMapper.toResource(room))
                .orElseThrow(() -> new DefaultControllerException(new DefaultErrorMessage(HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST,
                        String.format("Room with %s  does not exist in Database", roomId))));
    }
}
