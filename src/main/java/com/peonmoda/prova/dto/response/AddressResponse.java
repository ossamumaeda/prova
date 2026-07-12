package com.peonmoda.prova.dto.response;

import java.util.UUID;

import com.peonmoda.prova.entity.EnumTipoEndereco;

public record AddressResponse(

        UUID id,

        EnumTipoEndereco tipo,

        String codigoPostal,

        String logradouro,

        String numero,

        String complemento,

        String bairro,

        String municipio,

        String estado

) {
}
