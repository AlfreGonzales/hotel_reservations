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
import project.hotel_reservations.dto.hotel_admin.HotelAdminCreateDTO;
import project.hotel_reservations.dto.hotel_admin.HotelAdminResponseDTO;
import project.hotel_reservations.dto.hotel_admin.HotelAdminUpdateDTO;
import project.hotel_reservations.service.HotelAdminService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Hotel administrators")
@RestController
@RequestMapping("/hotel-admins")
@RequiredArgsConstructor
public class HotelAdminController {

    private final HotelAdminService service;

    @Operation(summary = "Create a hotel administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Hotel administrator created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping
    public ResponseEntity<HotelAdminResponseDTO> create(@Valid @RequestBody HotelAdminCreateDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @Operation(summary = "Get a list of all hotel administrators")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of hotel administrators returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<HotelAdminResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Get a hotel administrator by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel administrator found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hotel administrator not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<HotelAdminResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

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
    @PatchMapping("/{id}")
    public ResponseEntity<HotelAdminResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody HotelAdminUpdateDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @Operation(summary = "Delete a hotel administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Hotel administrator deleted successfully"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
