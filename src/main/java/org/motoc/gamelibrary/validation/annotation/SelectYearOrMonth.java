package org.motoc.gamelibrary.validation.annotation;

import org.motoc.gamelibrary.validation.SelectYearOrMonthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * A custom annotation in order to check the selection of minimal age. You must choose minAge XOR minMonth
 *
 * @author RouzicJ
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SelectYearOrMonthValidator.class)
public @interface SelectYearOrMonth {
    String message() default "If you picked a minimal age, you shouldn't pick a minimal month";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
