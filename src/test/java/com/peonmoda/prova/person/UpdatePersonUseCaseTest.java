package com.peonmoda.prova.person;

import com.peonmoda.prova.dto.request.UpdatePersonRequest;
import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.exception.PersonNotFoundException;
import com.peonmoda.prova.factory.PersonFactory;
import com.peonmoda.prova.mapper.PersonMapper;
import com.peonmoda.prova.service.PersonService;
import com.peonmoda.prova.usecase.UpdatePersonUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePersonUseCaseTest {

    @Mock
    private PersonService personService;

    @Mock
    private PersonMapper mapper;

    @InjectMocks
    private UpdatePersonUseCase useCase;

    @Test
    void shouldUpdatePersonSuccessfully() throws NotFoundException {

        UUID id = UUID.randomUUID();

        UpdatePersonRequest request = PersonFactory.updateRequest();
        PersonEntity entity = PersonFactory.createEntity();
        PersonResponse response = PersonFactory.createResponse();

        when(personService.searchById(id))
                .thenReturn(entity);

        when(personService.save(entity))
                .thenReturn(entity);

        when(mapper.converterParaResponse(entity))
                .thenReturn(response);

        PersonResponse result = useCase.execute(id, request);

        assertNotNull(result);
        assertEquals(response.nome(), result.nome());

        verify(personService).searchById(id);
        verify(mapper).updatePerson(request, entity);
        verify(personService).save(entity);
        verify(mapper).converterParaResponse(entity);
    }

    @Test
    void shouldThrowWhenPersonDoesNotExist() throws NotFoundException {

        UUID id = UUID.randomUUID();

        UpdatePersonRequest request = PersonFactory.updateRequest();

        when(personService.searchById(id))
                .thenThrow(new PersonNotFoundException("id"));

        assertThrows(
                PersonNotFoundException.class,
                () -> useCase.execute(id, request)
        );

        verify(personService, never()).save(any());
        verify(mapper, never()).updatePerson(any(), any());
    }

}
