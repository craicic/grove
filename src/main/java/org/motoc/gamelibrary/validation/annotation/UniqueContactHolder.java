package org.motoc.gamelibrary.validation.annotation;

import org.motoc.gamelibrary.validation.UniqueContactHolderValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * An annotation to make a contact have an unique holder
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueContactHolderValidator.class)
public @interface UniqueContactHolder {
    String message() default "A contact can only have a unique holder";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
