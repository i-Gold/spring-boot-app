package com.company.application.validation;

import com.company.application.controller.request.UserRegistrationRequest;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Component
public class RetypePasswordValidator implements ConstraintValidator<RetypePassword, UserRegistrationRequest> {

    @Override
    public void initialize(RetypePassword retypePassword) {
    }

    @Override
    public boolean isValid(UserRegistrationRequest userRegistrationRequest, ConstraintValidatorContext context) {
        if (!Objects.equals(userRegistrationRequest.getPassword(), userRegistrationRequest.getRetypePassword())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("{com.naturalprogrammer.spring.different.passwords}")
                    .addPropertyNode("password").addConstraintViolation()
                    .buildConstraintViolationWithTemplate("{com.naturalprogrammer.spring.different.passwords}")
                    .addPropertyNode("retypePassword").addConstraintViolation();
            return false;
        }
        return true;
    }
}