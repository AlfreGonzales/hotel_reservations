package project.hotel_reservations.dto.guest;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
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
