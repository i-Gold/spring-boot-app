package com.company.application.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

public class EnumValueValidator implements ConstraintValidator<Enum, String> {
    private Enum annotation;

    @Override
    public void initialize(Enum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (Objects.isNull(valueForValidation) || Objects.isNull(enumValues)) {
            return false;
        }
        return Arrays.stream(enumValues).anyMatch(enumValue -> valueForValidation.equals(enumValue.toString()));
    }
}