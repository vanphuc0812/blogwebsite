package com.example.blogwebsite.user.validation.annotation;

import com.example.blogwebsite.user.validation.validator.CorrectGenderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CorrectGenderValidator.class)
@Target(ElementType.FIELD)// dùng vô mảng nào
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrectGender {
    String message() default "{user.gender.incorrect}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
