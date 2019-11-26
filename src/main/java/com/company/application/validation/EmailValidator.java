package com.company.application.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class EmailValidator implements ConstraintValidator<Email, String> {
    @Override
    public void initialize(Email email) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email);
    }
}
