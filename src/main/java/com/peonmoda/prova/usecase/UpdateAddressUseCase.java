package com.peonmoda.prova.usecase;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peonmoda.prova.dto.request.UpdateAddressRequest;
import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.mapper.PersonMapper;
import com.peonmoda.prova.service.AddressService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateAddressUseCase {
    private final AddressService service;
    private final PersonMapper mapper;

    public AddressEntity execute(UUID id,UpdateAddressRequest address) {

        AddressEntity adressEntity = service.searchById(id);
        mapper.updateAddress(address, adressEntity);

        return service.save(adressEntity);

    }

}