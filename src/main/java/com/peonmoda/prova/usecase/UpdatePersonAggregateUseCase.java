package com.peonmoda.prova.usecase;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peonmoda.prova.dto.request.UpdateAddressRequest;
import com.peonmoda.prova.dto.request.UpdatePersonAggregateRequest;
import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.exception.DuplicateEmailException;
import com.peonmoda.prova.mapper.AddressMapper;
import com.peonmoda.prova.mapper.PersonMapper;
import com.peonmoda.prova.service.AddressService;
import com.peonmoda.prova.service.PersonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdatePersonAggregateUseCase {

        private final PersonService personService;
        private final AddressService addressService;

        private final AddressMapper addressMapper;
        private final PersonMapper personMapper;

        public PersonResponse execute(UUID id,
                        UpdatePersonAggregateRequest dto) throws NotFoundException {
                PersonEntity person = personService.searchById(id);
                validarEmail(dto.pessoa().email(), person);

                personMapper.updatePerson(dto.pessoa(), person);

                personService.save(person);     

                if (dto.enderecos().size() > 0) {
                        for (UpdateAddressRequest address : dto.enderecos()) {
                                AddressEntity adressEntity = addressService.searchById(address.id());
                                addressMapper.updateAddress(address, adressEntity);
                        }
                }

                return personMapper.converterParaResponse(person);

        }

        private void validarEmail(String email, PersonEntity person) {

                if (email.equals(person.getEmail())) {
                        return;
                }

                Optional<PersonEntity> existing = personService.searchByEmail(email);

                if (existing.isPresent() && !existing.get().getId().equals(person.getId())) {

                        throw new DuplicateEmailException(email);

                }

        }

}