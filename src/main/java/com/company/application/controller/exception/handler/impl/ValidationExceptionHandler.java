package com.company.application.controller.exception.handler.impl;

import com.company.application.controller.exception.ValidationException;
import com.company.application.controller.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.List;

@Component
public class ValidationExceptionHandler extends AbstractExceptionHandler<ValidationException> {

    public ValidationExceptionHandler() {
        super(ValidationException.class.getSimpleName());
    }

    @Override
    protected HttpStatus getStatus(ValidationException ex) {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    protected Collection<String> getErrors(ValidationException ex) {
        return null;
    }

    @Override
    protected List<FieldError> getFieldsErrors(ValidationException ex) {
        return ex.getFieldErrors();
    }

}
