package project.hotel_reservations.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import project.hotel_reservations.dto.reservation.ReservationCreateDTO;
import project.hotel_reservations.validation.annotation.ValidDateRange;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, ReservationCreateDTO> {
    @Override
    public boolean isValid(ReservationCreateDTO reservationCreateDTO, ConstraintValidatorContext constraintValidatorContext) {
        if (reservationCreateDTO.checkInDate() == null || reservationCreateDTO.checkOutDate() == null) {
            return true;
        }

        return reservationCreateDTO.checkOutDate().isAfter(reservationCreateDTO.checkInDate());
    }
}
