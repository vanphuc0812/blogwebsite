package com.example.blogwebsite.role.validation.annotation;

import com.example.blogwebsite.role.validation.validator.UniqueOperationCodeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueOperationCodeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UniqueOperationCode {
    String message() default "{operation.code.existed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
