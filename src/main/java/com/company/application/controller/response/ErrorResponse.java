package com.company.application.controller.response;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
public class ErrorResponse implements Serializable {

    private String exception;
    private String error;
    private String path;
    private String message;
    private Integer status;
    private Collection<String> errors;
    private List<FieldError> fieldErrors;

    public boolean incomplete() {
        return exception == null || status == null;
    }

}