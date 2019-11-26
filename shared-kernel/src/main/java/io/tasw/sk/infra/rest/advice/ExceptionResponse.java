package io.tasw.sk.infra.rest.advice;

import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.Iterator;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

@Getter
@AllArgsConstructor(access = PRIVATE, staticName = "of")
public class ExceptionResponse {

    private final String code;

    private final String message;

    public static ExceptionResponse from(Exception exception) {
        return of(exception.getClass().getSimpleName(), exception.getMessage());
    }
    
    public static ExceptionResponse from(ConstraintViolationException exception) {
        
        String beanName = exception.getConstraintViolations().stream()
            .map(ConstraintViolation::getLeafBean)
            .map(Object::getClass)
            .map(Class::getSimpleName)
            .findFirst()
            .orElse(exception.getClass().getSimpleName());
        
        return of(beanName, exception.getClass().getSimpleName());
    }

    public static ExceptionResponse from(ConstraintViolation<?> violation) {

        String property = null;
        Iterator<Node> iterator = violation.getPropertyPath().iterator();

        while (iterator.hasNext()) {
            property = iterator.next().getName();
        }

        String message = property + ": " + violation.getMessage();
        String code = violation.getMessageTemplate();

        if (code.startsWith("{")) {
            code = code.substring(1, code.length() - 1);
        }

        return of(code, message);
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = PRIVATE)
    public static final class ConstraintViolationResponse {

        @JsonUnwrapped
        private final ExceptionResponse operation;

        @Singular
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        @ApiModelProperty(position = 5)
        private final Collection<ExceptionResponse> details;

    }
}
