package project.hotel_reservations.dto.hotel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record HotelCreateDTO(
        @NotBlank(message = "Name must not be empty")
        String name,

        @NotBlank(message = "Address must not be empty")
        String address,

        @NotBlank(message = "Phone must not be empty")
        @Pattern(regexp = "\\d+", message = "Phone must contain only numbers")
        String phone,

        @Size(min = 1, message = "Email must not be empty")
        @Email(message = "Email must be valid")
        String email
) {
}
