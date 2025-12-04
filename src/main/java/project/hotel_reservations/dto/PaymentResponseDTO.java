package project.hotel_reservations.dto;

import lombok.Builder;
import project.hotel_reservations.model.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record PaymentResponseDTO(
        UUID id,
        BigDecimal totalAmount,
        LocalDateTime date,
        PaymentMethod paymentMethod,
        UUID paymentPlatformId
) {
}
