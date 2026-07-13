package com.peonmoda.prova.dto.request;

import java.time.LocalDate;
import com.peonmoda.prova.validation.annotation.Phone;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

public record UpdatePersonRequest(

                @NotBlank(message = "Nome é obrigatório.") String nome,

                @NotBlank(message = "E-mail é obrigatório.") @Email(message = "E-mail inválido.") String email,

                @Past(message = "Data de nascimento deve ser anterior à data atual.") LocalDate dataNascimento,

                @Phone @NotBlank(message = "Telefone é obrigatório.")
                String telefone

)

{
}
