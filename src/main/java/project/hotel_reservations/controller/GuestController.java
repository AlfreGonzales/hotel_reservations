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
import project.hotel_reservations.dto.guest.GuestCreateDTO;
import project.hotel_reservations.dto.guest.GuestResponseDTO;
import project.hotel_reservations.dto.guest.GuestUpdateDTO;
import project.hotel_reservations.service.GuestService;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing guests
 * Provides endpoints for creating, retrieving, updating, and deleting guests
 */
@Tag(name = "Guests")
@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService service;

    /**
     * Creates a new guest
     *
     * @param req DTO containing the guest data to create
     * @return ResponseEntity with the created guest DTO and HTTP status 201
     */
    @Operation(summary = "Create a guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Guest created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    @PostMapping
    public ResponseEntity<GuestResponseDTO> create(@Valid @RequestBody GuestCreateDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    /**
     * Retrieves a list of all registered guests
     *
     * @return ResponseEntity with the list of guest DTOs and HTTP status 200
     */
    @Operation(summary = "Get a list of all guests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of guests returned successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_ADMIN')")
    @GetMapping
    public ResponseEntity<List<GuestResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Retrieves a guest by its ID
     *
     * @param id UUID of the guest to retrieve
     * @return ResponseEntity with the found guest DTO and HTTP status 200
     */
    @Operation(summary = "Get a guest by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guest found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Guest not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST', 'HOTEL_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<GuestResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Updates an existing guest's information
     *
     * @param id  UUID of the guest to update
     * @param req DTO containing the updated guest data
     * @return ResponseEntity with the updated guest DTO and HTTP status 200
     */
    @Operation(summary = "Update a guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Guest updated successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Guest not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'GUEST')")
    @PatchMapping("/{id}")
    public ResponseEntity<GuestResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody GuestUpdateDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    /**
     * Deletes a guest by its ID
     *
     * @param id UUID of the guest to delete
     * @return ResponseEntity with no content and HTTP status 204 if deletion was successful
     */
    @Operation(summary = "Delete a guest")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Guest deleted successfully"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
