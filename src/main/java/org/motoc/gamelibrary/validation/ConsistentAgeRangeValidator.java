package org.motoc.gamelibrary.validation;

import org.motoc.gamelibrary.model.Game;
import org.motoc.gamelibrary.validation.annotation.ConsistentAgeRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * A custom validator in order to check the consistency of age range
 *
 * @author RouzicJ
 */
public class ConsistentAgeRangeValidator implements ConstraintValidator<ConsistentAgeRange, Game> {

    @Override
    public void initialize(ConsistentAgeRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(Game game, ConstraintValidatorContext constraintValidatorContext) {
        if (game.getMaxAge() == 0)
            if (game.getMinMonth() == 0)
                return true;
            else
                return game.getMaxAge()*12 > game.getMinMonth();
        return game.getMaxAge() > game.getMinAge();
    }
}
