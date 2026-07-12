package com.peonmoda.prova.exception;


public class DuplicateCpfException extends BusinessException {

    public DuplicateCpfException(String cpf) {
        super("Já existe uma pessoa cadastrada com o CPF: " + cpf);
    }

}
