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
        if (game.getAgeMax() == 0)
            return true;
        return game.getAgeMax() > game.getAgeMin();
    }
}
