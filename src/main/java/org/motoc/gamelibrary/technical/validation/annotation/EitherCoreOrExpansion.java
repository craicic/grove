package org.motoc.gamelibrary.technical.validation.annotation;

import org.motoc.gamelibrary.technical.validation.EitherCoreOrExpansionDtoValidator;
import org.motoc.gamelibrary.technical.validation.EitherCoreOrExpansionValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * A custom annotation in order to lock game to be either a core game or an expansion, not both.
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EitherCoreOrExpansionValidator.class, EitherCoreOrExpansionDtoValidator.class})
public @interface EitherCoreOrExpansion {
}
