package com.peonmoda.prova.person;

import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.factory.PersonFactory;
import com.peonmoda.prova.service.PersonService;
import com.peonmoda.prova.usecase.DeletePersonUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePersonUseCaseTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private DeletePersonUseCase useCase;

    @Test
    void shouldSoftDeletePersonSuccessfully() throws NotFoundException {

        UUID id = UUID.randomUUID();

        PersonEntity person = PersonFactory.createEntity();

        when(personService.searchById(id)).thenReturn(person);
        when(personService.save(person)).thenReturn(person);

        useCase.execute(id);

        assertFalse(person.getAtivo());

        person.getEnderecos().forEach(address ->
                assertFalse(address.getAtivo()));

        verify(personService).searchById(id);
        verify(personService).save(person);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenPersonDoesNotExist() throws NotFoundException {

        UUID id = UUID.randomUUID();

        when(personService.searchById(id))
                .thenThrow(new NotFoundException());

        assertThrows(
                NotFoundException.class,
                () -> useCase.execute(id)
        );

        verify(personService, never()).save(any());
    }

}