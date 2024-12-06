package com.example.auth.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.beans.BeanProperty;
import java.util.HashMap;
import java.util.Locale;
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
