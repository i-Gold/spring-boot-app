package com.company.application.controller.exception.handler.impl;

import com.company.application.controller.exception.EmailIsNotExistsException;
import com.company.application.controller.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.List;

@Component
public class EmailIsNotExistsExceptionHandler extends AbstractExceptionHandler<EmailIsNotExistsException> {

    public EmailIsNotExistsExceptionHandler() {
        super(EmailIsNotExistsException.class.getSimpleName());
    }

    @Override
    public HttpStatus getStatus(EmailIsNotExistsException ex) {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    protected Collection<String> getErrors(EmailIsNotExistsException ex) {
        return null;
    }

    @Override
    protected List<FieldError> getFieldsErrors(EmailIsNotExistsException ex) {
        return null;
    }

}
