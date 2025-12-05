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
import project.hotel_reservations.dto.payment_platform.PaymentPlatformCreateDTO;
import project.hotel_reservations.dto.payment_platform.PaymentPlatformResponseDTO;
import project.hotel_reservations.dto.payment_platform.PaymentPlatformUpdateDTO;
import project.hotel_reservations.service.PaymentPlatformService;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing payment platforms
 * Provides endpoints for creating, retrieving, updating, and deleting payment platforms
 */
@Tag(name = "Payment platforms")
@RestController
@RequestMapping("/payment-platforms")
@RequiredArgsConstructor
public class PaymentPlatformController {

    private final PaymentPlatformService service;

    /**
     * Creates a new payment platform
     *
     * @param req DTO containing the payment platform data to create
     * @return ResponseEntity with the created payment platform DTO and HTTP status 201
     */
    @Operation(summary = "Create a payment platform")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment platform created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping
    public ResponseEntity<PaymentPlatformResponseDTO> create(@Valid @RequestBody PaymentPlatformCreateDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    /**
     * Retrieves a list of all registered payment platforms
     *
     * @return ResponseEntity with the list of payment platform DTOs and HTTP status 200
     */
    @Operation(summary = "Get a list of all payment platforms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of payment platforms returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<PaymentPlatformResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Retrieves a payment platform by its ID
     *
     * @param id UUID of the payment platform to retrieve
     * @return ResponseEntity with the found payment platform DTO and HTTP status 200
     */
    @Operation(summary = "Get a payment platform by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment platform found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Payment platform not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentPlatformResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Updates an existing payment platform's information
     *
     * @param id  UUID of the payment platform to update
     * @param req DTO containing the updated payment platform data
     * @return ResponseEntity with the updated payment platform DTO and HTTP status 200
     */
    @Operation(summary = "Update a payment platform")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment platform updated successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Payment platform not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PaymentPlatformResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody PaymentPlatformUpdateDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    /**
     * Deletes a payment platform by its ID
     *
     * @param id UUID of the payment platform to delete
     * @return ResponseEntity with no content and HTTP status 204 if deletion was successful
     */
    @Operation(summary = "Delete a payment platform")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Payment platform deleted successfully"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
