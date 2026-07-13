package com.peonmoda.prova.usecase.person;

import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.mapper.PersonMapper;
import com.peonmoda.prova.service.PersonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetPersonByIdUseCase {

    private final PersonService personService;
    private final PersonMapper mapper;

    public PersonResponse execute(UUID id) throws NotFoundException {

        PersonEntity person = personService.searchById(id);

        return mapper.toResponse(person);

    }

}
