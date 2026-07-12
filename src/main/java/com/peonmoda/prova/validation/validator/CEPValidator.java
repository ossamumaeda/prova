package com.peonmoda.prova.validation.validator;

import com.peonmoda.prova.validation.annotation.CEP;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CEPValidator implements ConstraintValidator<CEP, String> {

    private static final String REGEX = "^\\d{8}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return true;
        }

        return value.matches(REGEX);
    }

}