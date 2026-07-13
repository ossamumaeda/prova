package com.peonmoda.prova.mapper;

import org.springframework.stereotype.Component;

import com.peonmoda.prova.dto.request.CreateAddressRequest;
import com.peonmoda.prova.dto.request.UpdateAddressRequest;
import com.peonmoda.prova.dto.response.AddressResponse;
import com.peonmoda.prova.entity.AddressEntity;

@Component
public class AddressMapper {

    public AddressEntity toUpdate(UpdateAddressRequest dto, AddressEntity address) {

        address.setTipo(dto.tipo());
        address.setCodigoPostal(dto.codigoPostal());
        address.setLogradouro(dto.logradouro());
        address.setNumero(dto.numero());
        address.setComplemento(dto.complemento());
        address.setBairro(dto.bairro());
        address.setMunicipio(dto.municipio());
        address.setEstado(dto.estado());

        return address;

    }

    public AddressEntity toEntity(CreateAddressRequest dto) {
        AddressEntity address = new AddressEntity();

        address.setTipo(dto.tipo());
        address.setCodigoPostal(dto.codigoPostal());
        address.setLogradouro(dto.logradouro());
        address.setNumero(dto.numero());
        address.setComplemento(dto.complemento());
        address.setBairro(dto.bairro());
        address.setMunicipio(dto.municipio());
        address.setEstado(dto.estado());
        return address;
    }

    public AddressResponse toResponse(AddressEntity entity) {
        AddressResponse response = new AddressResponse(
                entity.getId(),
                entity.getTipo(),
                entity.getCodigoPostal(),
                entity.getLogradouro(),
                entity.getNumero(),
                entity.getComplemento(),
                entity.getBairro(),
                entity.getMunicipio(),
                entity.getEstado());
        return response;
    }
}
