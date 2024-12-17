package org.exception.customException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
