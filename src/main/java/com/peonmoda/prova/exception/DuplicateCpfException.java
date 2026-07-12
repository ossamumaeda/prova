package com.peonmoda.prova.exception;

import org.springframework.http.HttpStatus;

public class DuplicateCpfException extends BusinessException {

    public DuplicateCpfException(String cpf) {
        super("Já existe uma pessoa cadastrada com o CPF: " + cpf, HttpStatus.CONFLICT);
    }

}
