package com.example.blogwebsite.user.validation.validator;

import com.example.blogwebsite.user.model.User;
import com.example.blogwebsite.user.repository.UserRepository;
import com.example.blogwebsite.user.validation.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository repository;
    private String message;

    public UniqueEmailValidator(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override

    public boolean isValid(String email, ConstraintValidatorContext context) {
        Optional<User> userOptional = repository.findByEmail(email);

        if (userOptional.isEmpty())
            return true;
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
