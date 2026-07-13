package com.peonmoda.prova.dto.request;

import java.util.UUID;

import com.peonmoda.prova.entity.EnumTipoEndereco;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
@Schema(description = "Update address request DTO")
public record UpdateAddressRequest(

        @Schema(
                description = "Identificador único do endereço.",
                example = "550e8400-e29b-41d4-a716-446655440000"
        )
        UUID id,

        @Schema(
                description = "Tipo do endereço.",
                example = "PRINCIPAL",
                allowableValues = {"PRINCIPAL", "ENTREGA", "COMERCIAL"}
        )
        @NotNull
        EnumTipoEndereco tipo,

        @Schema(
                description = "Nome do logradouro.",
                example = "Rua das Flores"
        )
        @NotBlank
        String logradouro,

        @Schema(
                description = "Número do imóvel.",
                example = "123"
        )
        String numero,

        @Schema(
                description = "Complemento do endereço.",
                example = "Apartamento 402, Bloco B",
                nullable = true
        )
        String complemento,

        @Schema(
                description = "Nome do bairro.",
                example = "Centro"
        )
        @NotBlank
        String bairro,

        @Schema(
                description = "Nome do município.",
                example = "Londrina"
        )
        @NotBlank
        String municipio,

        @Schema(
                description = "Sigla da unidade federativa (UF).",
                example = "PR",
                minLength = 2,
                maxLength = 2
        )
        @NotBlank
        String estado,

        @Schema(
                description = "CEP do endereço, contendo apenas números.",
                example = "86600218"
        )
        String codigoPostal

) {
}