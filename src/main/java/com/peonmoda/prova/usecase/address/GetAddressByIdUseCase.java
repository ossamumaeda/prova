package com.peonmoda.prova.usecase.address;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peonmoda.prova.dto.response.AddressResponse;
import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.exception.AddressNotRelatedToPersonException;
import com.peonmoda.prova.mapper.AddressMapper;
import com.peonmoda.prova.service.AddressService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAddressByIdUseCase {

    private final AddressService addressService;
    private final AddressMapper mapper;

    public AddressResponse execute(UUID personId,
                                   UUID addressId) {

        AddressEntity address =
                addressService.searchById(addressId);

        if (!address.getPessoa().getId().equals(personId)) {
            throw new AddressNotRelatedToPersonException();
        }

        return mapper.toResponse(address);
    }

}