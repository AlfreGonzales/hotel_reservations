package project.hotel_reservations.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record HotelAdminCreateDTO(
        @NotBlank(message = "Identification must not be empty")
        String identification,

        @NotBlank(message = "Name must not be empty")
        String name,

        @NotBlank(message = "Email must not be empty")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Position must not be empty")
        String position,

        @NotBlank(message = "Shift must not be empty")
        String shift
) {
}
