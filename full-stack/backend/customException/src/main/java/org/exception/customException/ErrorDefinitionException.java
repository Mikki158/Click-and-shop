package org.exception.customException;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ErrorDefinitionException extends RuntimeException {

    private final String errorCode;
    private final ErrorType type;
    private final Map<String, String> details;

    @Override
    public String getMessage() {
        return String.format("%s", type);
    }

    public enum Type{
        SERVICE,
        SYSTEM,
        VALIDATION,
        AUTHORIZATION;
    }
}