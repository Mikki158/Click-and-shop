package com.example.product.confic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WbeConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Разрешить все маршруты
                        .allowedOrigins("http://localhost:5173") // Разрешить запросы только с вашего фронтенда
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешённые HTTP-методы
                        .allowedHeaders("*") // Разрешить любые заголовки
                        .allowCredentials(true); // Разрешить использование куки
            }
        };
    }
}
