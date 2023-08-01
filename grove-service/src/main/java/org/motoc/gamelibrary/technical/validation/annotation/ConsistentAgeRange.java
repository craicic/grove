package org.motoc.gamelibrary.technical.validation.annotation;

import org.motoc.gamelibrary.technical.validation.ConsistentAgeRangeDtoValidator;
import org.motoc.gamelibrary.technical.validation.ConsistentAgeRangeValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * A custom annotation in order to check the consistency of age range
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConsistentAgeRangeValidator.class, ConsistentAgeRangeDtoValidator.class})
public @interface ConsistentAgeRange {
    String message() default "Minimal age must be less than maximal age";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
