package com.company.application.validation;

import com.company.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Autowired
    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueEmail uniqueEmail) {
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(email);
    }
}
