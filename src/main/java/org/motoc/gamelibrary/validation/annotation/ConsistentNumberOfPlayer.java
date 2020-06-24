package org.motoc.gamelibrary.validation.annotation;

import org.motoc.gamelibrary.validation.ConsistentNumberOfPlayerDtoValidator;
import org.motoc.gamelibrary.validation.ConsistentNumberOfPlayerValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * A custom annotation in order to check the consistency of number of player parameters
 *
 * @author RouzicJ
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
