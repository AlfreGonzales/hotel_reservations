package project.hotel_reservations.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record GuestResponseDTO(
        UUID id,
        String identification,
        String name,
        String email,
        LocalDate birthDate,
        String nationality,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
