package com.example.blogwebsite.blogpost.validation.annotation;

import com.example.blogwebsite.blogpost.validation.validator.UniqueTitleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueTitleValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTitle {
    String message() default "{blog.title.existed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
