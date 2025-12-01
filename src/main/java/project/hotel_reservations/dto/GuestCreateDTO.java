package project.hotel_reservations.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import project.hotel_reservations.validation.annotation.ValidAdult;

import java.time.LocalDate;

public record GuestCreateDTO(
        @NotBlank(message = "Identification must not be empty")
        String identification,

        @NotBlank(message = "Name must not be empty")
        String name,

        @NotBlank(message = "Email must not be empty")
        @Email(message = "Email must be valid")
        String email,

        @NotNull(message = "BirthDate must not be empty")
        @ValidAdult(message = "The user must be over 18 years of age")
        LocalDate birthDate,

        @NotBlank(message = "Nationality must not be empty")
        String nationality
) {
}
