package org.motoc.gamelibrary.validation;

import org.motoc.gamelibrary.model.Contact;
import org.motoc.gamelibrary.validation.annotation.UniqueContactHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * A validator to make a contact have an unique holder
 */
public class UniqueContactHolderValidator implements ConstraintValidator<UniqueContactHolder, Contact> {

    @Override
    public void initialize(UniqueContactHolder constraintAnnotation) {

    }

    @Override
    public boolean isValid(Contact contact, ConstraintValidatorContext constraintValidatorContext) {
        return (contact.getCreator() != null ^ contact.getSeller() != null
                ^ contact.getAccount() != null ^ contact.getPublisher() != null) ||
                (contact.getCreator() == null && contact.getSeller() == null && contact.getAccount()
                        == null && contact.getPublisher() == null);
    }
}
