package com.peonmoda.prova.exception;

import org.springframework.http.HttpStatus;

public class AdressNotFoundException extends BusinessException {

    public AdressNotFoundException() {
        super("Endereço não existe" , HttpStatus.NOT_FOUND);
    }
    
}
