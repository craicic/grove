package org.motoc.gamelibrary.technical.validation;

import org.motoc.gamelibrary.domain.dto.GameDto;
import org.motoc.gamelibrary.technical.validation.annotation.ConsistentNumberOfPlayer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * A custom validator in order to check the consistency of number of player parameters
 */
public class ConsistentNumberOfPlayerDtoValidator implements ConstraintValidator<ConsistentNumberOfPlayer, GameDto> {

    @Override
    public void initialize(ConsistentNumberOfPlayer constraintAnnotation) {

    }

    @Override
    public boolean isValid(GameDto game, ConstraintValidatorContext constraintValidatorContext) {
        if (game.getMaxNumberOfPlayer() == 0)
            return true;
        return game.getMaxNumberOfPlayer() >= game.getMinNumberOfPlayer();
    }
}
