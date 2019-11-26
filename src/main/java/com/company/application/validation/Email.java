package com.company.application.validation;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface Email {

    String message() default "Email is not valid";

    Class[] groups() default {};

    Class[] payload() default {};
}
