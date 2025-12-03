package project.hotel_reservations.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ReservationResponseDTO(
        UUID id,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer peopleCount,
        String status,
        UUID roomId,
        UUID guestId
) {
}
