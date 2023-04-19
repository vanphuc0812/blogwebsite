package com.example.blogwebsite.role.validation.validator;

import com.example.blogwebsite.role.model.Operation;
import com.example.blogwebsite.role.repository.OperationRepository;
import com.example.blogwebsite.role.validation.annotation.UniqueOperationCode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class UniqueOperationCodeValidator
        implements ConstraintValidator<UniqueOperationCode, String> {
    private final OperationRepository repository;
    private String message;

    public UniqueOperationCodeValidator(OperationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UniqueOperationCode constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String code, ConstraintValidatorContext context) {
        Optional<Operation> operationOpt = repository.findByCode(code);

        if (operationOpt.isEmpty())
            return true;

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
