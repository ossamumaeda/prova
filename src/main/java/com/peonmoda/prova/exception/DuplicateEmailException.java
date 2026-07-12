package com.peonmoda.prova.exception;


public class DuplicateEmailException extends BusinessException {

    public DuplicateEmailException(String email) {
        super("Já existe uma pessoa cadastrada com o e-mail: " + email);
    }

}