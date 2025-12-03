package project.hotel_reservations.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import project.hotel_reservations.validation.validator.DateRangeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    String message() default "The check-out date must be later than the check-in date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
