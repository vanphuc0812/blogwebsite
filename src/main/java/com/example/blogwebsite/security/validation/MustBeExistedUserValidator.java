package com.example.blogwebsite.security.validation;

import com.example.blogwebsite.user.model.User;
import com.example.blogwebsite.user.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MustBeExistedUserValidator implements ConstraintValidator<MustBeExistedUser, String> {
    private final UserRepository userRepository;
    private String message;

    public MustBeExistedUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(MustBeExistedUser target) {
        this.message = target.message();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user != null) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
