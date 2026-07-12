package com.peonmoda.prova.validation.validator;


import com.peonmoda.prova.util.CPFUtils;
import com.peonmoda.prova.validation.annotation.CPF;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return CPFUtils.isCPFValid(value);
    }

}