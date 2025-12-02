package project.hotel_reservations.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HotelUpdateDTO(
        @Size(min = 1, message = "Name must not be empty")
        String name,

        @Size(min = 1, message = "Address must not be empty")
        String address,

        @Size(min = 1, message = "Phone must not be empty")
        @Pattern(regexp = "\\d+", message = "Phone must contain only numbers")
        String phone,

        @Size(min = 1, message = "Email must not be empty")
        @Email(message = "Email must be valid")
        String email
) {
}
