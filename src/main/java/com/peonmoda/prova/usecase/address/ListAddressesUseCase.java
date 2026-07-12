package com.peonmoda.prova.usecase.address;

import java.util.List;
import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peonmoda.prova.dto.response.AddressResponse;
import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.exception.AddressNotRelatedToPersonException;
import com.peonmoda.prova.mapper.PersonMapper;
import com.peonmoda.prova.service.AddressService;
import com.peonmoda.prova.service.PersonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListAddressesUseCase {

    private final PersonService personService;
    private final PersonMapper mapper;

    public List<AddressResponse> execute(UUID personId) throws NotFoundException {

        PersonEntity person =
                personService.searchById(personId);

        return person.getEnderecos()
                .stream()
                .filter(AddressEntity::getAtivo)
                .map(mapper::converterAddresParaResponse)
                .toList();
    }

}
