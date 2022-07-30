package org.motoc.gamelibrary.technical.validation;

import org.motoc.gamelibrary.domain.model.Game;
import org.motoc.gamelibrary.technical.validation.annotation.EitherCoreOrExpansion;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EitherCoreOrExpansionValidator implements ConstraintValidator<EitherCoreOrExpansion, Game> {

    @Override
    public void initialize(EitherCoreOrExpansion constraintAnnotation) {

    }

    @Override
    public boolean isValid(Game game, ConstraintValidatorContext constraintValidatorContext) {
        if (game.getExpansions() != null)
            if (game.getExpansions().isEmpty())
                return true;
        return game.getCoreGame() == null;
    }
}
