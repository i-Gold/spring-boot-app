package com.company.application.controller.exception.handler.impl;

import com.company.application.controller.exception.TokenIsExpiredException;
import com.company.application.controller.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.List;

@Component
public class TokenIsExpiredExceptionHandler extends AbstractExceptionHandler<TokenIsExpiredException> {

    public TokenIsExpiredExceptionHandler() {
        super(TokenIsExpiredException.class.getSimpleName());
    }

    @Override
    public HttpStatus getStatus(TokenIsExpiredException ex) {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    protected Collection<String> getErrors(TokenIsExpiredException ex) {
        return null;
    }

    @Override
    protected List<FieldError> getFieldsErrors(TokenIsExpiredException ex) {
        return null;
    }

}
