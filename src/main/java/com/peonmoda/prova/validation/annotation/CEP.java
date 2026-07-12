package com.peonmoda.prova.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.peonmoda.prova.validation.validator.CEPValidator;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CEPValidator.class)
public @interface CEP {

    String message() default "CEP inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}