package com.peonmoda.prova.usecase.address;

import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peonmoda.prova.dto.request.CreateAddressRequest;
import com.peonmoda.prova.dto.response.AddressResponse;
import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.mapper.AddressMapper;
import com.peonmoda.prova.service.AddressService;
import com.peonmoda.prova.service.PersonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateAddressUseCase {

    private final PersonService personService;
    private final AddressService addressService;
    private final AddressMapper addressMapper;

    public AddressResponse execute(UUID personId,
                                   CreateAddressRequest dto) throws NotFoundException {

        PersonEntity person = personService.searchById(personId);

        AddressEntity address = addressMapper.toEntity(dto);

        address.setPessoa(person);

        AddressEntity saved = addressService.save(address);

        return addressMapper.toResponse(saved);
    }

}
