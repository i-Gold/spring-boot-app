package com.company.application.controller.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.List;

public class ValidationException extends RuntimeException {

    @Getter
    @Setter
    private List<FieldError> fieldErrors;

    public ValidationException(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

}
