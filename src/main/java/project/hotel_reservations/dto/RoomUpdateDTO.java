package project.hotel_reservations.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RoomUpdateDTO(
        @Size(min = 1, message = "Code must not be empty")
        String code,

        Integer capacity,

        @Size(min = 1, message = "Type must not be empty")
        String type,

        BigDecimal price,

        @Size(min = 1, message = "Description must not be empty")
        String description,

        Boolean available
) {
}
