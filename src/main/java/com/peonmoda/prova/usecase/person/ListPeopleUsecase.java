package com.peonmoda.prova.usecase.person;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.mapper.PersonMapper;
import com.peonmoda.prova.service.PersonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListPeopleUsecase {

    private final PersonService personService;
    private final PersonMapper mapper;

    public List<PersonResponse> execute() throws NotFoundException {

        List<PersonEntity> people = personService.getAll();

        List<PersonResponse> peopleResponse = new ArrayList<PersonResponse>();

        for(PersonEntity person: people){
            peopleResponse.add(mapper.toResponse(person));
        }

        return peopleResponse;

    }

}
