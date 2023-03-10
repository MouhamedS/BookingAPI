package com.kata.Booking.API.application.controllers;

import com.kata.Booking.API.application.mapper.ReservationResourceMapper;
import com.kata.Booking.API.application.resources.ReservationCreateResource;
import com.kata.Booking.API.application.resources.ReservationResource;
import com.kata.Booking.API.domain.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="Reservation API", description = "API to do reservation operations")
@Slf4j
@RestController(value = "ReservationController")
@RequestMapping(value = "/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final BookingService bookingService;

    private final ReservationResourceMapper reservationResourceMapper;

    @Operation(summary = "Get all the reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All reservations retrieved", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = ReservationResource.class)))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid Transaction", content = {
                    @Content
            }),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)

    })
    @GetMapping( value = "/room/{roomId}",produces = "application/json" )
    public List<ReservationResource> getAllReservations(@PathVariable("roomId")Long roomId){
        log.info("Call endpoint GET /api/v1/reservations to retrieve all reservations");
        return reservationResourceMapper.toResources(bookingService.getAllReservationsByRoomId(roomId));
    }

    @Operation(summary = "Create a new reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation created", content = {
                @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ReservationResource.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = {
                    @Content
            }),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)
    })
    @PostMapping(value = "/room/{roomId}" ,produces = "application/json", consumes = "application/json")
    public ReservationResource createReservation(@Parameter(description = "Room identification number",required = true, example = "1145")@PathVariable ("roomId") Long roomId,
                                                     @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, description = "The reservation to be created")
                                                     @RequestBody ReservationCreateResource resource){
        log.info("Call endpoint POST /api/v1/reservations to create new reservation with request {}", resource);
        return reservationResourceMapper.toResource(bookingService
                .createReservation(resource.dateCheckIn(), resource.dateCheckOut(), roomId));
    }

    @Operation(summary = "Modify a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation modified", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Boolean.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = {
                    @Content
            }),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)
    })
    @PutMapping(value = "/room/{roomId}", produces = "application/json", consumes = "application/json")
    public Boolean modifyReservation(@Parameter(description = "Room identification number",required = true, example = "1145")@PathVariable ("roomId") Long roomId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,description = "The modified reservayion")
                                         @RequestBody ReservationResource resource){
        log.info("Call endpoint PUT /api/v1/reservations to modify a reservation with request {}", resource);
        return bookingService
                .modifyReservation(reservationResourceMapper.fromResource(resource), roomId);
    }

    @Operation(summary = "Cancel a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation acncelled", content = {
                    @Content
            }),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = {
                    @Content
            }),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content)
    })
    @DeleteMapping(value = "/room/{roomId}/{reservationNumber}",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReservation(@Parameter(required = true, description = "The reservation number" , example = "AHAJ4")@PathVariable(name = "reservationNumber")String reservationNumber,
                                  @Parameter(required = true, description = "The room id" , example = "1")@PathVariable(name = "roomId")Long roomId){
        log.info("Call endpoint PUT /api/v1/reservations to delete reservation with parameters roomId {} reservationNumber {}", roomId, reservationNumber);
        bookingService
                .cancelReservation(reservationNumber, roomId);
    }


}
