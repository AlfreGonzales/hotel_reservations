package project.hotel_reservations.dto;

import lombok.Builder;
import project.hotel_reservations.model.PaymentMethod;
import project.hotel_reservations.model.Reservation;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record PaymentCreateDTO(
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Reservation reservation,
        UUID paymentPlatformId
) {
}
