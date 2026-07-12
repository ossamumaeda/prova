package com.peonmoda.prova.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peonmoda.prova.dto.request.UpdateAddressRequest;
import com.peonmoda.prova.dto.response.AddressResponse;
import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.exception.AddressNotRelatedToPersonException;
import com.peonmoda.prova.mapper.AddressMapper;
import com.peonmoda.prova.service.AddressService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateAddressUseCase {
    private final AddressService service;
    private final AddressMapper mapper;

    public AddressResponse execute(UUID personId, UUID addressId, UpdateAddressRequest address) {

        AddressEntity addressEntity = service.searchById(addressId);
        if (!addressEntity.getPessoa().getId().equals(personId)) {
            throw new AddressNotRelatedToPersonException();
        }
        mapper.updateAddress(address, addressEntity);

        service.save(addressEntity);
        return mapper.converterAddresParaResponse(addressEntity);
    }

}