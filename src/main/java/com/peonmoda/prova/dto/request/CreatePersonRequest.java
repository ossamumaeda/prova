package com.peonmoda.prova.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;

import com.peonmoda.prova.validation.annotation.CPF;
import com.peonmoda.prova.validation.annotation.Phone;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Create person request DTO")
public record CreatePersonRequest(

        @Schema(
                description = "CPF da pessoa, contendo apenas números.",
                example = "12345678909"
        )
        @CPF
        @NotBlank(message = "CPF é obrigatório.")
        String cpf,

        @Schema(
                description = "Nome completo da pessoa.",
                example = "João da Silva"
        )
        @NotBlank(message = "Nome é obrigatório.")
        String nome,

        @Schema(
                description = "Endereço de e-mail da pessoa.",
                example = "joao.silva@email.com"
        )
        @NotBlank(message = "E-mail é obrigatório.")
        @Email(message = "E-mail inválido.")
        String email,

        @Schema(
                description = "Data de nascimento da pessoa.",
                example = "1995-03-10"
        )
        @Past(message = "Data de nascimento deve ser anterior à data atual.")
        LocalDate dataNascimento,

        @Schema(
                description = "Telefone da pessoa, contendo DDD.",
                example = "43999998888"
        )
        @Phone
        @NotBlank(message = "Telefone é obrigatório.")
        String telefone,

        @Schema(
                description = "Lista de endereços da pessoa."
        )
        @NotEmpty(message = "A pessoa deve possuir pelo menos um endereço.")
        @Valid
        List<CreateAddressRequest> enderecos

) {
}