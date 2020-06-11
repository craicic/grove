package org.motoc.gamelibrary.validation;

import org.motoc.gamelibrary.model.Category;
import org.motoc.gamelibrary.validation.annotation.EitherChildOrParent;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * A custom validator in order to lock category to be either a parent or a child, not both.
 *
 * @author RouzicJ
 */
public class EitherChildOrParentValidator implements ConstraintValidator<EitherChildOrParent, Category> {

    @Override
    public void initialize(EitherChildOrParent constraintAnnotation) {
    }

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext constraintValidatorContext) {
        return (category.getChildren().isEmpty() || category.getParent() == null);
    }
}
