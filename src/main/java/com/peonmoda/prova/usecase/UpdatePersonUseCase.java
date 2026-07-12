package com.peonmoda.prova.usecase;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peonmoda.prova.dto.request.UpdatePersonRequest;
import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.exception.DuplicateEmailException;
import com.peonmoda.prova.mapper.PersonMapper;
import com.peonmoda.prova.service.PersonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdatePersonUseCase {

    private final PersonService personService;
    private final PersonMapper mapper;

    public PersonResponse execute(UUID id, UpdatePersonRequest dto) throws NotFoundException {
        PersonEntity person = personService.searchById(id);

        validarEmail(dto.email(), person);

        mapper.updatePerson(dto, person);

        personService.save(person);
        return mapper.converterParaResponse(person);
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