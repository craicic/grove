package org.motoc.gamelibrary.validation;

import org.motoc.gamelibrary.dto.CategoryDto;
import org.motoc.gamelibrary.validation.annotation.EitherChildOrParent;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EitherChildOrParentDtoValidator implements ConstraintValidator<EitherChildOrParent, CategoryDto> {
    @Override
    public void initialize(EitherChildOrParent constraintAnnotation) {
    }

    @Override
    public boolean isValid(CategoryDto category, ConstraintValidatorContext constraintValidatorContext) {
        return (category.getChildren().isEmpty() || category.getParent() == null);
    }
}
