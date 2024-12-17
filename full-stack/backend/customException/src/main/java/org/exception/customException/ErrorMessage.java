package org.exception.customException;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.function.Supplier;

@Data
@Accessors(fluent = true, chain = true)
@RequiredArgsConstructor
public class ErrorMessage {
    private final Exception ex;
    private final HttpStatus status;

    private Supplier<String> errorCode;
    private Supplier<Map<String, String>> details;
}