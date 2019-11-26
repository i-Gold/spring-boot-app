package com.company.application.validation;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RetypePasswordValidator.class)
public @interface RetypePassword {

    String message() default "{com.naturalprogrammer.spring.different.passwords}";

    Class[] groups() default {};

    Class[] payload() default {};
}