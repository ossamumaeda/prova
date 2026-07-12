package com.peonmoda.prova.exception;

import org.springframework.http.HttpStatus;

public class PersonNotFoundException extends BusinessException{

    public PersonNotFoundException(String message) {
        super(message,HttpStatus.CONFLICT);
    }

}
