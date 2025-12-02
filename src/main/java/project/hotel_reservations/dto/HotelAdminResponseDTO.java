package project.hotel_reservations.dto;

import java.time.LocalDateTime;
import java.util.UUID;

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
