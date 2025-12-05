package project.hotel_reservations.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.hotel_reservations.dto.reservation.PayReservationDTO;
import project.hotel_reservations.dto.reservation.ReservationCreateDTO;
import project.hotel_reservations.dto.reservation.ReservationResponseDTO;
import project.hotel_reservations.model.ReservationStatus;
import project.hotel_reservations.service.ReservationService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * REST controller for managing reservations
 * Provides endpoints for creating, retrieving, confirming, and cancelling reservations.
 */
@Tag(name = "Reservations")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

    /**
     * Creates a new reservation
     *
     * @param req DTO containing the reservation data to create
     * @return ResponseEntity with the created reservation DTO and HTTP status 201
     */
    @Operation(summary = "Create a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping
    public ResponseEntity<ReservationResponseDTO> create(@Valid @RequestBody ReservationCreateDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    /**
     * Retrieves a list of all reservations
     *
     * @return ResponseEntity with the list of reservation DTOs and HTTP status 200
     */
    @Operation(summary = "Get a list of all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reservations returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Retrieves a reservation by its ID
     *
     * @param id UUID of the reservation to retrieve
     * @return ResponseEntity with the found reservation DTO and HTTP status 200
     */
    @Operation(summary = "Get a reservation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reservation not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Confirms an existing reservation and processes its payment
     *
     * @param id  UUID of the reservation to confirm
     * @param req DTO containing the payment information
     * @return ResponseEntity with the confirmed reservation DTO and HTTP status 200
     */
    @Operation(summary = "Confirm a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation confirmed successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reservation not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<ReservationResponseDTO> confirmReservation(@PathVariable UUID id, @Valid @RequestBody PayReservationDTO req) {
        return ResponseEntity.ok(service.confirmReservation(id, req));
    }

    /**
     * Cancels an existing reservation
     *
     * @param id UUID of the reservation to cancel
     * @return ResponseEntity with the cancelled reservation DTO and HTTP status 200
     */
    @Operation(summary = "Cancel a reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation cancelled successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reservation not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ReservationResponseDTO> cancelReservation(@PathVariable UUID id) {
        return ResponseEntity.ok(service.cancelReservation(id));
    }

    /**
     * Retrieves a list of all reservations by rooms
     *
     * @return ResponseEntity with the list of reservation DTOs and HTTP status 200
     */
    @Operation(summary = "Get a list of all reservations by rooms")
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<List<ReservationResponseDTO>> findReservationsByRoom(@PathVariable UUID roomId) {
        return ResponseEntity.ok(service.findReservationsByRoom(roomId));
    }

    /**
     * Retrieves a list of all reservations grouped by payment method
     *
     * @return ResponseEntity with the list of reservation DTOs and HTTP status 200
     */
    @Operation(summary = "Get a list of all reservations grouped by payment method")
    @GetMapping("/group-method")
    public ResponseEntity<Map<ReservationStatus, List<ReservationResponseDTO>>> getReservationsGroupedByPaymentMethod() {
        return ResponseEntity.ok(service.getReservationsGroupedByPaymentMethod());
    }
}
