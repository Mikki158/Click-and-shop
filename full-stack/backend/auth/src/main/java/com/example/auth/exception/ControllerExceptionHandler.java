package com.example.auth.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ResponseEntityFactory responseEntityFactory;

    @ExceptionHandler(ErrorDefinitionException.class)
    public ResponseEntity<?> executionException(
            ErrorDefinitionException ex
    ) {
        ErrorMessage errorMessage = new ErrorMessage(ex, HttpStatus.BAD_REQUEST)
                        .errorCode(() -> ex.getErrorCode())
                        .details(() -> ex.getDetails());
        return responseEntityFactory.create(errorMessage);
    }
}
