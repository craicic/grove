package org.motoc.gamelibrary.validation;

import org.motoc.gamelibrary.dto.GameDto;
import org.motoc.gamelibrary.validation.annotation.SelectYearOrMonth;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SelectYearOrMonthDtoValidator implements ConstraintValidator<SelectYearOrMonth, GameDto> {

    @Override
    public void initialize(SelectYearOrMonth constraintAnnotation) {

    }

    @Override
    public boolean isValid(GameDto game, ConstraintValidatorContext constraintValidatorContext) {
        return game.getMinAge() == 0 ^ game.getMinMonth() == 0;
    }
}
