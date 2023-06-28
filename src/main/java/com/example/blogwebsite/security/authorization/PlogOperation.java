package com.example.blogwebsite.security.authorization;

import com.example.blogwebsite.role.model.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PlogOperation {
    String name() default "";

    Operation.Type type() default Operation.Type.FETCH;
}
