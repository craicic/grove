package org.motoc.gamelibrary.validation;

import org.motoc.gamelibrary.dto.GameDto;
import org.motoc.gamelibrary.validation.annotation.ConsistentAgeRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * A custom validator in order to check the consistency of age range
 */
public class ConsistentAgeRangeDtoValidator implements ConstraintValidator<ConsistentAgeRange, GameDto> {

    @Override
    public void initialize(ConsistentAgeRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(GameDto game, ConstraintValidatorContext constraintValidatorContext) {
        if (game.getMaxAge() == 0) {
            return true;
        }
        if (game.getMinMonth() >= 0) {
            return game.getMaxAge() * 12 > game.getMinMonth();
        } else if (game.getMinAge() >= 0) {
            return game.getMaxAge() > game.getMinAge();
        }
        return true;
    }
}
