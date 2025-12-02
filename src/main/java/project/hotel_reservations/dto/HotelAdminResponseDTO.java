package project.hotel_reservations.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record HotelAdminResponseDTO(
        UUID id,
        String identification,
        String name,
        String email,
        String position,
        String shift,
        UUID hotelId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
