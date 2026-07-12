package com.peonmoda.prova.validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import com.peonmoda.prova.validation.validator.PhoneValidator;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {

    String message() default "Telefone inválido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}