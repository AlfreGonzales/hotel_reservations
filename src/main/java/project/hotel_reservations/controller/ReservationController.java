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
import project.hotel_reservations.dto.PayReservationDTO;
import project.hotel_reservations.dto.ReservationCreateDTO;
import project.hotel_reservations.dto.ReservationResponseDTO;
import project.hotel_reservations.service.ReservationService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Reservations")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService service;

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

    @Operation(summary = "Get a list of all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reservations returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

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
}
