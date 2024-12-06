package com.example.auth.exception;

import java.io.IOException;
import java.util.Properties;

public class ErrorMessageLoader {
    private Properties properties;

    public ErrorMessageLoader() {
        properties = new Properties();
        try{
            properties.load(getClass().getClassLoader().getResourceAsStream("errors.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Unable", e);
        }
    }

    public String getMessage(String errorCode) {
        return properties.getProperty(errorCode, "Unknown error");
    }
}
