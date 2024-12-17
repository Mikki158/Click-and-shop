package org.exception.customException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseEntityFactory {

    public ResponseEntity create(ErrorMessage errorMessage) {
        String errorCode = errorMessage.errorCode().get();
        ErrorMessageLoader loader = new ErrorMessageLoader();
        String errorText = loader.getMessage(errorCode);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", errorCode);
        responseBody.put("message", errorText);
        responseBody.put("type", errorMessage.ex().getMessage());
        responseBody.put("details", errorMessage.details().get());

        return new ResponseEntity<>(responseBody, errorMessage.status());
    }
}

