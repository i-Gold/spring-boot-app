package com.company.application.controller.exception.handler.impl;

import com.company.application.controller.exception.TokenDoesNotMatchException;
import com.company.application.controller.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.List;

@Component
public class TokenDoesNotMatchExceptionHandler extends AbstractExceptionHandler<TokenDoesNotMatchException> {

    public TokenDoesNotMatchExceptionHandler() {
        super(TokenDoesNotMatchException.class.getSimpleName());
    }

    @Override
    public HttpStatus getStatus(TokenDoesNotMatchException ex) {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    protected Collection<String> getErrors(TokenDoesNotMatchException ex) {
        return null;
    }

    @Override
    protected List<FieldError> getFieldsErrors(TokenDoesNotMatchException ex) {
        return null;
    }

}
