package com.peonmoda.prova.dto.request;

import java.time.LocalDate;
import com.peonmoda.prova.validation.annotation.Phone;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
@Schema(description = "Update person request DTO")
public record UpdatePersonRequest(

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
        String telefone

) {
}
