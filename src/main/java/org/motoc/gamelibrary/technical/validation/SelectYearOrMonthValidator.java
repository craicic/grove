package org.motoc.gamelibrary.technical.validation;

import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.technical.validation.annotation.SelectYearOrMonth;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * A custom validator in order to check the selection of minimal age. You must choose minAge (in year) XOR minMonth
 */
public class SelectYearOrMonthValidator implements ConstraintValidator<SelectYearOrMonth, Game> {
    @Override
    public void initialize(SelectYearOrMonth constraintAnnotation) {

    }

    @Override
    public boolean isValid(Game game, ConstraintValidatorContext constraintValidatorContext) {
        return game.getMinAge() == 0 || game.getMinMonth() == 0;
    }
}
