package com.peonmoda.prova.dto.request;


import com.peonmoda.prova.entity.EnumTipoEndereco;
import com.peonmoda.prova.validation.annotation.CEP;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAddressRequest(

        @NotNull(message = "Tipo de endereço é obrigatório.")
        EnumTipoEndereco tipo,

        @CEP
        @Size(min = 8, max = 8, message = "CEP deve possuir 8 dígitos.")
        String codigoPostal,

        @NotBlank(message = "Logradouro é obrigatório.")
        String logradouro,

        @NotBlank(message = "Número é obrigatório.")
        String numero,

        String complemento,

        @NotBlank(message = "Bairro é obrigatório.")
        String bairro,

        @NotBlank(message = "Município é obrigatório.")
        String municipio,

        @NotBlank(message = "Estado é obrigatório.")
        @Size(min = 2, max = 2, message = "Estado deve possuir a sigla com 2 caracteres.")
        String estado

) {
}