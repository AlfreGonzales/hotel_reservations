package project.hotel_reservations.dto.payment_platform;

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
