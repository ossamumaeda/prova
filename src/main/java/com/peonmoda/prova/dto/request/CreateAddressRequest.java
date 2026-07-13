package com.peonmoda.prova.dto.request;

import com.peonmoda.prova.entity.EnumTipoEndereco;
import com.peonmoda.prova.validation.annotation.CEP;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Create address request")
public record CreateAddressRequest(
                @Schema(description = "Tipo do endereço.", example = "PRINCIPAL", allowableValues = {
                                "PRINCIPAL", "ENTREGA",
                                "COMERCIAL" }) @NotNull(message = "Tipo de endereço é obrigatório.") EnumTipoEndereco tipo,

                @Schema(description = "CEP do endereço, contendo apenas números.", example = "86600218") @CEP @Size(min = 8, max = 8, message = "CEP deve possuir 8 dígitos.") String codigoPostal,

                @Schema(description = "Nome do logradouro.", example = "Rua das Flores") @NotBlank(message = "Logradouro é obrigatório.") String logradouro,

                @Schema(description = "Número do imóvel.", example = "123") @NotBlank(message = "Número é obrigatório.") String numero,

                @Schema(description = "Complemento do endereço.", example = "Apartamento 402, Bloco B", nullable = true) String complemento,

                @Schema(description = "Nome do bairro.", example = "Centro") @NotBlank(message = "Bairro é obrigatório.") String bairro,

                @Schema(description = "Nome do município.", example = "Londrina") @NotBlank(message = "Município é obrigatório.") String municipio,

                @Schema(description = "Sigla da unidade federativa (UF).", example = "PR", minLength = 2, maxLength = 2) @NotBlank(message = "Estado é obrigatório.") @Size(min = 2, max = 2, message = "Estado deve possuir a sigla com 2 caracteres.") String estado

        ) {
}