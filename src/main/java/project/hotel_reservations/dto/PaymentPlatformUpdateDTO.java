package project.hotel_reservations.dto;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record PaymentPlatformUpdateDTO(
        @Size(min = 1, message = "Name must not be empty")
        String name,

        @Size(min = 1, message = "Code must not be empty")
        String code,

        Boolean active
) {
}
