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
import project.hotel_reservations.dto.room.RoomCreateDTO;
import project.hotel_reservations.dto.room.RoomResponseDTO;
import project.hotel_reservations.dto.room.RoomUpdateDTO;
import project.hotel_reservations.service.RoomService;

import java.util.List;
import java.util.UUID;

@Tag(name = "Rooms")
@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

    @Operation(summary = "Create a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Room created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping
    public ResponseEntity<RoomResponseDTO> create(@Valid @RequestBody RoomCreateDTO req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @Operation(summary = "Get a list of all rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of rooms returned successfully")
    })
    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Get a room by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room found"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Room not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Update a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Room updated successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Room not found",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody RoomUpdateDTO req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @Operation(summary = "Delete a room")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Room deleted successfully"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a list of all deleted rooms")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of rooms returned successfully")
    })
    @GetMapping("/deleted")
    public ResponseEntity<List<RoomResponseDTO>> findAllDeleted() {
        return ResponseEntity.ok(service.findAllDeleted());
    }
}
