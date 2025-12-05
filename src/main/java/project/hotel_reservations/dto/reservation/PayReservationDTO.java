package project.hotel_reservations.dto.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import project.hotel_reservations.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record PayReservationDTO(
        @NotNull(message = "Total amount must not be empty")
        BigDecimal totalAmount,

        @NotNull(message = "Payment method must not be empty")
        PaymentMethod paymentMethod,

        UUID paymentPlatformId
) {
}
