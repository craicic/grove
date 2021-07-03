package org.motoc.gamelibrary.validation.annotation;

import org.motoc.gamelibrary.validation.ConsistentAgeRangeDtoValidator;
import org.motoc.gamelibrary.validation.ConsistentAgeRangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
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
