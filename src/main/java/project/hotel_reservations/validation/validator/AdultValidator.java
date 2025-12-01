package project.hotel_reservations.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import project.hotel_reservations.validation.annotation.ValidAdult;

import java.time.LocalDate;
import java.time.Period;

public class AdultValidator implements ConstraintValidator<ValidAdult, LocalDate> {
    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) {
            return true;
        }
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }
}
