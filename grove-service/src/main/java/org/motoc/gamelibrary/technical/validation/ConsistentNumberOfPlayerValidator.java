package org.motoc.gamelibrary.technical.validation;

import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.technical.validation.annotation.ConsistentNumberOfPlayer;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * A custom validator in order to check the consistency of number of player parameters
 */
public class ConsistentNumberOfPlayerValidator implements ConstraintValidator<ConsistentNumberOfPlayer, Game> {

    @Override
    public void initialize(ConsistentNumberOfPlayer constraintAnnotation) {

    }

    @Override
    public boolean isValid(Game game, ConstraintValidatorContext constraintValidatorContext) {
        if (game.getMaxNumberOfPlayer() == 0)
            return true;
        return game.getMaxNumberOfPlayer() >= game.getMinNumberOfPlayer();
    }
}

