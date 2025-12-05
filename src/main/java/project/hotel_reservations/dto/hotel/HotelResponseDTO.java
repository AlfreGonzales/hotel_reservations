package project.hotel_reservations.dto.hotel;

import lombok.Builder;

import java.util.UUID;

@Builder
public record HotelResponseDTO(
        UUID id,
        String name,
        String address,
        String phone,
        String email
) {
}
