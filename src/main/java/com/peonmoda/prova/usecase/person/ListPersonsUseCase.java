package com.peonmoda.prova.usecase.person;
import java.util.List;
import org.springframework.stereotype.Service;

import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.mapper.PersonMapper;
import com.peonmoda.prova.service.PersonService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ListPersonsUseCase {

    private final PersonService personService;
    private final PersonMapper mapper;

    public List<PersonResponse> execute() {

        return personService.getAll()
                .stream()
                .map(mapper::toResponse)
                .toList();

    }

}