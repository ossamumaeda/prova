package com.peonmoda.prova.factory;


import com.peonmoda.prova.dto.request.CreateAddressRequest;
import com.peonmoda.prova.dto.request.UpdateAddressRequest;
import com.peonmoda.prova.dto.response.AddressResponse;
import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.entity.EnumTipoEndereco;

import java.time.LocalDateTime;
import java.util.UUID;

public final class AddressFactory {

    public static CreateAddressRequest createRequest() {

        return new CreateAddressRequest(
                EnumTipoEndereco.PRINCIPAL,
                "87050130",
                "Avenida Paiçandu",
                "718",
                "1º andar",
                "Zona 03",
                "Maringá",
                "PR"
        );
    }

    public static UpdateAddressRequest updateRequest(UUID id) {

        return new UpdateAddressRequest(
                id,
                EnumTipoEndereco.PRINCIPAL,
                "87050130",
                "Avenida Brasil",
                "999",
                "Sala 10",
                "Centro",
                "Maringá",
                "PR"
        );
    }

    public static AddressEntity createEntity() {

        AddressEntity entity = new AddressEntity();

        entity.setId(UUID.randomUUID());
        entity.setTipo(EnumTipoEndereco.PRINCIPAL);
        entity.setCodigoPostal("87050130");
        entity.setLogradouro("Avenida Paiçandu");
        entity.setNumero("718");
        entity.setComplemento("1º andar");
        entity.setBairro("Zona 03");
        entity.setMunicipio("Maringá");
        entity.setEstado("PR");
        entity.setAtivo(true);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }

    public static AddressResponse createResponse() {

        return new AddressResponse(
                UUID.randomUUID(),
                EnumTipoEndereco.PRINCIPAL,
                "87050130",
                "Avenida Paiçandu",
                "718",
                "1º andar",
                "Zona 03",
                "Maringá",
                "PR"
        );
    }

}