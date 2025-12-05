package project.hotel_reservations.dto.payment_platform;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PaymentPlatformCreateDTO(
        @NotBlank(message = "Name must not be empty")
        String name,

        @NotBlank(message = "Code must not be empty")
        String code
) {
}
