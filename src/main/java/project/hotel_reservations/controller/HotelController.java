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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.hotel_reservations.dto.hotel.HotelCreateDTO;
import project.hotel_reservations.dto.hotel.HotelResponseDTO;
import project.hotel_reservations.dto.hotel.HotelUpdateDTO;
import project.hotel_reservations.service.HotelService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing hotels
 * Provides endpoints for creating, retrieving, updating, and deleting hotels
 */
@Tag(name = "Hotels")
@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService service;

    /**
     * Creates a new hotel
     *
     * @param req DTO containing the hotel data to create
     * @return ResponseEntity with the created hotel DTO and HTTP status 201
     */
    @Operation(summary = "Create a hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hotel created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HotelResponseDTO> create(@Valid @RequestBody HotelCreateDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    /**
     * Retrieves a list of all registered hotels
     *
     * @return ResponseEntity with the list of hotel DTOs and HTTP status 200
     */
    @Operation(summary = "Get a list of all hotels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of hotels returned successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    @GetMapping
    public ResponseEntity<List<HotelResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Retrieves a hotel by its ID
     *
     * @param id UUID of the hotel to retrieve
     * @return ResponseEntity with the found hotel DTO and HTTP status 200
     */
    @Operation(summary = "Get a hotel by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hotel not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST', 'HOTEL_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Updates an existing hotel's information
     *
     * @param id  UUID of the hotel to update
     * @param req DTO containing the updated hotel data
     * @return ResponseEntity with the updated hotel DTO and HTTP status 200
     */
    @Operation(summary = "Update a hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel updated successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hotel not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody HotelUpdateDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    /**
     * Deletes a hotel by its ID
     *
     * @param id UUID of the hotel to delete
     * @return ResponseEntity with no content and HTTP status 204 if deletion was successful
     */
    @Operation(summary = "Delete a hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Hotel deleted successfully"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves the total earnings of the hotel
     *
     * @param id UUID of the hotel
     * @return ResponseEntity with the total earnings content and HTTP status 200
     */
    @Operation(summary = "Total earnings")
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_ADMIN')")
    @GetMapping("/{id}/total-earnings")
    public ResponseEntity<BigDecimal> getTotalEarnings(@PathVariable UUID id) {
        BigDecimal totalEarnings = service.getTotalEarningsByHotel(id);
        return ResponseEntity.ok(totalEarnings);
    }
}
