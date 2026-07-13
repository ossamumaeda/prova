package com.peonmoda.prova.usecase.person;
import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.service.PersonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeletePersonUseCase {

    private final PersonService personService;

    @Transactional
    public void execute(UUID id) throws NotFoundException {

        PersonEntity person = personService.searchById(id);

        person.setAtivo(false);

        person.getEnderecos()
                .forEach(endereco -> endereco.setAtivo(false));

        personService.save(person);

    }

}
