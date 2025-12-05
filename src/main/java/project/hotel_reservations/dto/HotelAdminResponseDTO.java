package project.hotel_reservations.dto;

import lombok.Builder;
import project.hotel_reservations.model.HotelAdminShift;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record HotelAdminResponseDTO(
        UUID id,
        String identification,
        String name,
        String email,
        String position,
        HotelAdminShift shift,
        UUID hotelId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
