package org.motoc.gamelibrary.technical.validation;

import org.motoc.gamelibrary.domain.model.Loan;
import org.motoc.gamelibrary.technical.validation.annotation.ConsistentDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * A custom validator in order to check the consistency of loan date (is start date before end date)
 */
public class ConsistentDateTimeValidator implements ConstraintValidator<ConsistentDateTime, Loan> {

    @Override
    public void initialize(ConsistentDateTime constraintAnnotation) {
    }

    @Override
    public boolean isValid(Loan loan, ConstraintValidatorContext constraintValidatorContext) {
        return loan.getLoanStartTime().isBefore(loan.getLoanEndTime());
    }

}