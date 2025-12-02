package project.hotel_reservations.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record HotelAdminUpdateDTO(
        @Size(min = 1, message = "Identification must not be empty")
        String identification,

        @Size(min = 1, message = "Name must not be empty")
        String name,

        @Size(min = 1, message = "Email must not be empty")
        @Email(message = "Email must be valid")
        String email,

        @Size(min = 1, message = "Position must not be empty")
        String position,

        @Size(min = 1, message = "Shift must not be empty")
        String shift,

        UUID hotelId
) {
}
