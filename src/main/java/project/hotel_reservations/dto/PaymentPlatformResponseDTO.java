package project.hotel_reservations.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PaymentPlatformResponseDTO(
        UUID id,
        String name,
        String code,
        Boolean active
) {
}
