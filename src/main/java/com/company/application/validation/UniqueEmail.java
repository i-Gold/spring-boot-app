package com.company.application.validation;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {

    String message() default "Email is already exists";

    Class[] groups() default {};

    Class[] payload() default {};
}
