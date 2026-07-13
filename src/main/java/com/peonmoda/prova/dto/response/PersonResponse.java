package com.peonmoda.prova.dto.response;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Person response DTO")
public record PersonResponse(

        @Schema(
                description = "Identificador único da pessoa.",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        UUID id,

        @Schema(
                description = "CPF da pessoa, contendo apenas números.",
                example = "12345678909"
        )
        String cpf,

        @Schema(
                description = "Nome completo da pessoa.",
                example = "João da Silva"
        )
        String nome,

        @Schema(
                description = "Endereço de e-mail da pessoa.",
                example = "joao.silva@email.com"
        )
        String email,

        @Schema(
                description = "Data de nascimento da pessoa.",
                example = "1995-03-10"
        )
        LocalDate dataNascimento,

        @Schema(
                description = "Telefone da pessoa, contendo DDD.",
                example = "43999998888"
        )
        String telefone,

        @Schema(
                description = "Lista de endereços da pessoa."
        )
        List<AddressResponse> enderecos

) {
}