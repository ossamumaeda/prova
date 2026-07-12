package com.peonmoda.prova.dto.response;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PersonResponse(

        UUID id,

        String cpf,

        String nome,

        String email,

        LocalDate dataNascimento,

        String telefone,

        List<AddressResponse> enderecos

) {
}
