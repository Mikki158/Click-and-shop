package org.exception.customException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class AppConfig {

    @Bean
    @ConditionalOnMissingBean
    public MessageSource errorsMessageSource() {
        ReloadableResourceBundleMessageSource messageSourceError = new ReloadableResourceBundleMessageSource();
        messageSourceError.setBasenames("classpath:errors");
        messageSourceError.setUseCodeAsDefaultMessage(true);
        messageSourceError.setDefaultLocale(Locale.getDefault());
        messageSourceError.setDefaultEncoding("UTF-8");
        return messageSourceError;
    }
}
