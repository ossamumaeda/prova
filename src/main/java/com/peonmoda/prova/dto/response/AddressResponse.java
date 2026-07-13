package com.peonmoda.prova.dto.response;

import java.util.UUID;

import com.peonmoda.prova.entity.EnumTipoEndereco;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Address response DTO")
public record AddressResponse(

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
        EnumTipoEndereco tipo,

        @Schema(
                description = "CEP do endereço, contendo apenas números.",
                example = "86600218"
        )
        String codigoPostal,

        @Schema(
                description = "Nome do logradouro.",
                example = "Rua das Flores"
        )
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
        String bairro,

        @Schema(
                description = "Nome do município.",
                example = "Londrina"
        )
        String municipio,

        @Schema(
                description = "Sigla da unidade federativa (UF).",
                example = "PR"
        )
        String estado

) {
}