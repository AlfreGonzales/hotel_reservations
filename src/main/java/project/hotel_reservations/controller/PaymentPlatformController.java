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
import project.hotel_reservations.dto.PaymentPlatformCreateDTO;
import project.hotel_reservations.dto.PaymentPlatformResponseDTO;
import project.hotel_reservations.dto.PaymentPlatformUpdateDTO;
import project.hotel_reservations.service.PaymentPlatformService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Payment platforms")
@RestController
@RequestMapping("/payment-platforms")
@RequiredArgsConstructor
public class PaymentPlatformController {

    private final PaymentPlatformService service;

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

    @Operation(summary = "Get a list of all payment platforms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of payment platforms returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<PaymentPlatformResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

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
