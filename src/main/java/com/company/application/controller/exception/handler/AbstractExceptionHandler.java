package com.company.application.controller.exception.handler;

import com.company.application.controller.response.ErrorResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Component
public abstract class AbstractExceptionHandler<T extends Throwable> {

    @Getter
    @Setter
    private String exceptionName;

    public AbstractExceptionHandler(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    protected String getMessage(T ex) {
        return ex.getMessage();
    }

    protected abstract HttpStatus getStatus(T ex);

    protected abstract Collection<String> getErrors(T ex);

    protected abstract List<FieldError> getFieldsErrors(T ex);

    public ErrorResponse getErrorResponse(HttpServletRequest httpServletRequest, T ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        HttpStatus status = getStatus(ex);
        if (status != null) {
            errorResponse.setStatus(status.value());
            errorResponse.setError(status.getReasonPhrase());
        }
        errorResponse.setMessage(getMessage(ex));
        errorResponse.setPath(httpServletRequest.getRequestURI());
        errorResponse.setErrors(getErrors(ex));
        errorResponse.setFieldErrors(getFieldsErrors(ex));
        return errorResponse;
    }

}
