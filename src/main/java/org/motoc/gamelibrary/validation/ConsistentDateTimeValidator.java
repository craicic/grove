package org.motoc.gamelibrary.validation;

import org.motoc.gamelibrary.model.Loan;
import org.motoc.gamelibrary.validation.annotation.ConsistentDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConsistentDateTimeValidator implements ConstraintValidator<ConsistentDateTime, Loan> {

    @Override
    public void initialize(ConsistentDateTime constraintAnnotation) {
    }

    @Override
    public boolean isValid(Loan loan, ConstraintValidatorContext constraintValidatorContext) {
        return loan.getLoanStartTime().isBefore(loan.getLoanEndTime());
    }

}
