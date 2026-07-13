package com.peonmoda.prova.exception;

import org.springframework.http.HttpStatus;

public class EmptyAddresses extends BusinessException {

    public EmptyAddresses(String message) {
        super(message,HttpStatus.CONFLICT);
    }

}