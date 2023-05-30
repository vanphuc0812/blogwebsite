package com.example.blogwebsite.blogpost.validation.validator;

import com.example.blogwebsite.blogpost.model.Blog;
import com.example.blogwebsite.blogpost.repository.BlogRepository;
import com.example.blogwebsite.blogpost.validation.annotation.UniqueTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class UniqueTitleValidator implements ConstraintValidator<UniqueTitle, String> {

    private final BlogRepository repository;
    private String message;

    public UniqueTitleValidator(BlogRepository blogRepository) {
        this.repository = blogRepository;
    }

    @Override
    public void initialize(UniqueTitle constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        Optional<Blog> blogOptional = repository.findByTitle(title);

        if (blogOptional.isEmpty())
            return true;
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
