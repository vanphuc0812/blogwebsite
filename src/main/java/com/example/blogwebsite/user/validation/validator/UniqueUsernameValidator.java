package com.example.blogwebsite.user.validation.validator;

import com.example.blogwebsite.user.model.User;
import com.example.blogwebsite.user.repository.UserRepository;
import com.example.blogwebsite.user.validation.annotation.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository repository;
    private String message;

    public UniqueUsernameValidator(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override

    public boolean isValid(String username, ConstraintValidatorContext context) {
        Optional<User> userOptional = repository.findByUsername(username);

        if (userOptional.isEmpty())
            return true;
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
