package project.hotel_reservations.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import project.hotel_reservations.validation.annotation.ValidDateRange;

import java.time.LocalDate;
import java.util.UUID;

@ValidDateRange
@Builder
public record ReservationCreateDTO(
        @NotNull(message = "Check-in date must not be empty")
        LocalDate checkInDate,

        @NotNull(message = "Check-out date must not be empty")
        LocalDate checkOutDate,

        @NotNull(message = "People count in date must not be empty")
        Integer peopleCount,

        @NotNull(message = "Room ID must not be empty")
        UUID roomId,

        @NotNull(message = "Guest ID must not be empty")
        UUID guestId
) {
}
