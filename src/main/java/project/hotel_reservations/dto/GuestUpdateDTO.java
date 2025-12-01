package project.hotel_reservations.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import project.hotel_reservations.validation.annotation.ValidAdult;

import java.time.LocalDate;

public record GuestUpdateDTO(
        @Size(min = 1, message = "Identification must not be empty")
        String identification,

        @Size(min = 1, message = "Name must not be empty")
        String name,

        @Size(min = 1, message = "Email must not be empty")
        @Email(message = "Email must be valid")
        String email,

        @ValidAdult(message = "The user must be over 18 years of age")
        LocalDate birthDate,

        @Size(min = 1, message = "Nationality must not be empty")
        String nationality
) {
}
