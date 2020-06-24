package org.motoc.gamelibrary.validation.annotation;

import org.motoc.gamelibrary.validation.EitherChildOrParentDtoValidator;
import org.motoc.gamelibrary.validation.EitherChildOrParentValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * A custom annotation in order to lock category to be either a parent or a child, not both.
 *
 * @author RouzicJ
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EitherChildOrParentValidator.class, EitherChildOrParentDtoValidator.class})
public @interface EitherChildOrParent {

    String message() default "A category can be either a child or a parent, not both";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
