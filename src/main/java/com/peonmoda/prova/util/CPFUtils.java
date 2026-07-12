package com.peonmoda.prova.util;


public class CPFUtils {
    
    static public boolean isCPFValid(String value){

        if (value == null || value.isBlank()) {
            return true;
        }

        String cpf = value.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }

        int resto = 11 - (soma % 11);

        int primeiroDigito = resto >= 10 ? 0 : resto;

        if (primeiroDigito != cpf.charAt(9) - '0') {
            return false;
        }

        soma = 0;

        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }

        resto = 11 - (soma % 11);

        int segundoDigito = resto >= 10 ? 0 : resto;

        return segundoDigito == cpf.charAt(10) - '0';
    }

}
