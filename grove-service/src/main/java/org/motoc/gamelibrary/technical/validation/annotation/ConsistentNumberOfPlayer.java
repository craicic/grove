package org.motoc.gamelibrary.technical.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.motoc.gamelibrary.technical.validation.ConsistentNumberOfPlayerDtoValidator;
import org.motoc.gamelibrary.technical.validation.ConsistentNumberOfPlayerValidator;

import java.lang.annotation.*;

/**
 * A custom annotation in order to check the consistency of number of player parameters
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConsistentNumberOfPlayerValidator.class, ConsistentNumberOfPlayerDtoValidator.class})
public @interface ConsistentNumberOfPlayer {
    String message() default "Minimum number of player must be less than maximum number of player";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
