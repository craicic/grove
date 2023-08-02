package org.motoc.gamelibrary.technical.validation.annotation;

import org.motoc.gamelibrary.technical.validation.SelectYearOrMonthDtoValidator;
import org.motoc.gamelibrary.technical.validation.SelectYearOrMonthValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * A custom annotation in order to check the selection of minimal age. You must choose minAge XOR minMonth
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SelectYearOrMonthValidator.class, SelectYearOrMonthDtoValidator.class})
public @interface SelectYearOrMonth {
    String message() default "If you select a minimal age, minimal month must be equal zero";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
