package com.example.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BotKeyValidator {

    @Value("${bot.secret-key}")
    private String expectedKey;

    public boolean isValid(String authorizationHeader) {
        if (authorizationHeader == null) {
            return false;
        }

        if (!authorizationHeader.startsWith("BotApiKey ")) {
            return false;
        }

        String key = authorizationHeader.substring("BotApiKey ".length()).trim();
        return expectedKey.equals(key);
    }
}
