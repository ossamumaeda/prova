package com.peonmoda.prova.dto.request;

import java.util.UUID;

import com.peonmoda.prova.entity.EnumTipoEndereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAddressRequest(

        UUID id,

        @NotNull
        EnumTipoEndereco tipo,

        @NotBlank
        String logradouro,

        String numero,

        String complemento,

        @NotBlank
        String bairro,

        @NotBlank
        String municipio,

        @NotBlank
        String estado,

        String codigoPostal

) {
}