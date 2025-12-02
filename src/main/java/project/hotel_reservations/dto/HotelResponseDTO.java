package project.hotel_reservations.dto;

import java.util.UUID;

public record HotelResponseDTO(
        UUID id,
        String name,
        String address,
        String phone,
        String email
) {
}
