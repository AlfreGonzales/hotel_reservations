package project.hotel_reservations.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import project.hotel_reservations.validation.validator.AdultValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdultValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAdult {
    String message() default "The user must be of legal age";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
