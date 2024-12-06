package com.example.auth.exception;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Detail {
    String key();
    String value();
}
