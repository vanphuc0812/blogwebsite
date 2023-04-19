package com.example.blogwebsite.user.validation.validator;

import com.example.blogwebsite.user.model.User;
import com.example.blogwebsite.user.validation.annotation.CorrectGender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CorrectGenderValidator implements ConstraintValidator<CorrectGender, String> {

    private String message;

    @Override
    public void initialize(CorrectGender constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext context) {
        if (gender == null) return true;
        for (User.Gender g : User.Gender.values()) {
            if (g.name().equals(gender)) {
                return true;
            }
        }
        return false;
    }
}
