package com.company.application.controller.exception.handler;

import com.company.application.controller.response.ErrorResponse;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice<T extends Throwable> {
    private static final Logger LOGGER = Logger.getLogger(ExceptionHandlerControllerAdvice.class);

    private ErrorResponseComposer<T> errorResponseComposer;

    public ExceptionHandlerControllerAdvice(ErrorResponseComposer<T> errorResponseComposer) {
        this.errorResponseComposer = errorResponseComposer;
    }

    @RequestMapping(produces = "application/json")
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleException(HttpServletRequest httpServletRequest, T ex) throws T {
        LOGGER.error(httpServletRequest, ex);
        ErrorResponse errorResponse = errorResponseComposer.compose(httpServletRequest, ex).orElseThrow(() -> ex);
        if (errorResponse.incomplete()) {
            throw ex;
        }
        errorResponse.setException(ex.getClass().getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }

}