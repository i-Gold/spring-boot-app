package com.company.application.controller.exception.handler;

import com.company.application.controller.response.ErrorResponse;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ErrorResponseComposer<T extends Throwable> {

    private final Map<String, AbstractExceptionHandler<T>> handlers;

    public ErrorResponseComposer(List<AbstractExceptionHandler<T>> handlers) {
        this.handlers = handlers.stream().collect(
                Collectors.toMap(AbstractExceptionHandler::getExceptionName, Function.identity(), (handler1, handler2) ->
                        AnnotationAwareOrderComparator.INSTANCE.compare(handler1, handler2) < 0 ? handler1 : handler2));
    }

    @SuppressWarnings("unchecked")
    Optional<ErrorResponse> compose(HttpServletRequest httpServletRequest, T ex) {
        AbstractExceptionHandler<T> handler = null;
        while (ex != null) {
            handler = handlers.get(ex.getClass().getSimpleName());
            if (handler != null) {
                break;
            }
            ex = (T) ex.getCause();
        }
        return handler != null ? Optional.of(handler.getErrorResponse(httpServletRequest, ex)) : Optional.empty();
    }

}
