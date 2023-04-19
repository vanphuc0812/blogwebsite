package com.example.blogwebsite.role.validation.validator;

import com.example.blogwebsite.role.model.Operation;
import com.example.blogwebsite.role.repository.OperationRepository;
import com.example.blogwebsite.role.validation.annotation.UniqueOperationName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class UniqueOperationNameValidator
        implements ConstraintValidator<UniqueOperationName, String> {
    private final OperationRepository repository;
    private String message;

    public UniqueOperationNameValidator(OperationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueOperationName constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        Optional<Operation> operationOpt = repository.findByName(name);

        if (operationOpt.isEmpty())
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
