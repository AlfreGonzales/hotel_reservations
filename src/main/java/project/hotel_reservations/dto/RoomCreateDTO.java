package project.hotel_reservations.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record RoomCreateDTO(
        @NotBlank(message = "Code must not be empty")
        String code,

        @NotNull(message = "Capacity must not be empty")
        Integer capacity,

        @NotBlank(message = "Type must not be empty")
        String type,

        @NotNull(message = "Price must not be empty")
        BigDecimal price,

        @NotBlank(message = "Description must not be empty")
        String description,

        @NotNull(message = "Hotel ID must not be empty")
        UUID hotelId
) {
}
