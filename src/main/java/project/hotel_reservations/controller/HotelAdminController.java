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
import project.hotel_reservations.dto.hotel_admin.HotelAdminCreateDTO;
import project.hotel_reservations.dto.hotel_admin.HotelAdminResponseDTO;
import project.hotel_reservations.dto.hotel_admin.HotelAdminUpdateDTO;
import project.hotel_reservations.service.HotelAdminService;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing hotel administrators
 * Provides endpoints for creating, retrieving, updating, and deleting hotel administrators
 */
@Tag(name = "Hotel administrators")
@RestController
@RequestMapping("/hotel-admins")
@RequiredArgsConstructor
public class HotelAdminController {

    private final HotelAdminService service;

    /**
     * Creates a new hotel administrator
     *
     * @param req DTO containing the hotel administrator data to create
     * @return ResponseEntity with the created hotel administrator DTO and HTTP status 201
     */
    @Operation(summary = "Create a hotel administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hotel administrator created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HotelAdminResponseDTO> create(@Valid @RequestBody HotelAdminCreateDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    /**
     * Retrieves a list of all registered hotel administrators
     *
     * @return ResponseEntity with the list of hotel administrator DTOs and HTTP status 200
     */
    @Operation(summary = "Get a list of all hotel administrators")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of hotel administrators returned successfully")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<HotelAdminResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Retrieves a hotel administrator by its ID
     *
     * @param id UUID of the hotel administrator to retrieve
     * @return ResponseEntity with the found hotel administrator DTO and HTTP status 200
     */
    @Operation(summary = "Get a hotel administrator by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel administrator found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hotel administrator not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<HotelAdminResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Updates an existing hotel administrator's information
     *
     * @param id  UUID of the hotel administrator to update
     * @param req DTO containing the updated hotel administrator data
     * @return ResponseEntity with the updated hotel administrator DTO and HTTP status 200
     */
    @Operation(summary = "Update a hotel administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel administrator updated successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hotel administrator not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'HOTEL_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<HotelAdminResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody HotelAdminUpdateDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    /**
     * Deletes a hotel administrator by its ID
     *
     * @param id UUID of the hotel administrator to delete
     * @return ResponseEntity with no content and HTTP status 204 if deletion was successful
     */
    @Operation(summary = "Delete a hotel administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Hotel administrator deleted successfully"),
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
