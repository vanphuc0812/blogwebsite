package com.example.blogwebsite.role.validation.validator;

import com.example.blogwebsite.role.model.UserGroup;
import com.example.blogwebsite.role.repository.UserGroupRepository;
import com.example.blogwebsite.role.validation.annotation.UniqueUserGroupName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class UniqueUserGroupNameValidator
        implements ConstraintValidator<UniqueUserGroupName, String> {
    private final UserGroupRepository repository;
    private String message;

    public UniqueUserGroupNameValidator(UserGroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueUserGroupName constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Optional<UserGroup> userGroup = repository.findByName(name);

        if (userGroup.isEmpty())
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
