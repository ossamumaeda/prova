package com.peonmoda.prova.exception;

import org.springframework.http.HttpStatus;

public class AddressNotRelatedToPersonException extends BusinessException {

    public AddressNotRelatedToPersonException() {
        super("Endereço não vinculado com a pessoa" , HttpStatus.CONFLICT);
    }
    
}
