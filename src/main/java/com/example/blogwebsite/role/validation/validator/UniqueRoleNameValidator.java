package com.example.blogwebsite.role.validation.validator;

import com.example.blogwebsite.role.model.Role;
import com.example.blogwebsite.role.repository.RoleRepository;
import com.example.blogwebsite.role.validation.annotation.UniqueRoleName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class UniqueRoleNameValidator
        implements ConstraintValidator<UniqueRoleName, String> {
    private final RoleRepository repository;
    private String message;

    public UniqueRoleNameValidator(RoleRepository roleRepository) {
        this.repository = roleRepository;
    }

    @Override
    public void initialize(UniqueRoleName constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Optional<Role> roleOpt = repository.findByName(name);

        if (roleOpt.isEmpty())
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
