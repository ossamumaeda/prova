package com.peonmoda.prova.person;

import com.peonmoda.prova.dto.request.CreatePersonRequest;
import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.exception.BusinessException;
import com.peonmoda.prova.exception.DuplicateCpfException;
import com.peonmoda.prova.exception.DuplicateEmailException;
import com.peonmoda.prova.exception.EmptyAddresses;
import com.peonmoda.prova.factory.PersonFactory;
import com.peonmoda.prova.mapper.PersonMapper;
import com.peonmoda.prova.service.PersonService;
import com.peonmoda.prova.usecase.CreatePersonUsecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CreatePersonUseCaseTest {

    @Mock
    private PersonService personService;

    @Mock
    private PersonMapper mapper;

    @InjectMocks
    private CreatePersonUsecase useCase;

    @Test
    void shouldCreatePersonSuccessfully() {

        CreatePersonRequest request = PersonFactory.createRequest();
        PersonEntity entity = PersonFactory.createEntity();
        PersonResponse response = PersonFactory.createResponse();

        when(personService.searchByCpf(request.cpf())).thenReturn(Optional.empty());
        when(personService.searchByEmail(request.email())).thenReturn(Optional.empty());
        when(mapper.converterParaEntidade(request)).thenReturn(entity);
        when(personService.save(any(PersonEntity.class))).thenReturn(entity);
        when(mapper.converterParaResponse(entity)).thenReturn(response);

        PersonResponse result = useCase.execute(request);

        assertNotNull(result);
        assertEquals(response.nome(), result.nome());
        assertEquals(response.email(), result.email());

        ArgumentCaptor<PersonEntity> captor = ArgumentCaptor.forClass(PersonEntity.class);

        verify(personService).save(captor.capture());

        PersonEntity saved = captor.getValue();

        assertEquals(entity.getNome(), saved.getNome());

        saved.getEnderecos().forEach(address -> assertEquals(saved, address.getPessoa()));
    }

    @Test
    void shouldThrowDuplicateCpfException() {

        CreatePersonRequest request = PersonFactory.createRequest();

        when(personService.searchByCpf(request.cpf()))
                .thenReturn(Optional.of(PersonFactory.createEntity()));

        assertThrows(
                DuplicateCpfException.class,
                () -> useCase.execute(request));

        verify(personService, never()).save(any());
        verify(mapper, never()).converterParaEntidade(any());
    }

    @Test
    void shouldThrowDuplicateEmailException() {

        CreatePersonRequest request = PersonFactory.createRequest();

        when(personService.searchByCpf(request.cpf()))
                .thenReturn(Optional.empty());

        when(personService.searchByEmail(request.email()))
                .thenReturn(Optional.of(PersonFactory.createEntity()));

        assertThrows(
                DuplicateEmailException.class,
                () -> useCase.execute(request));

        verify(personService, never()).save(any());
        verify(mapper, never()).converterParaEntidade(any());
    }

    @Test
    void shouldThrowBusinessExceptionWhenPersonHasNoAddresses() {

        CreatePersonRequest request = PersonFactory.createRequestWithoutAddresses();

        when(personService.searchByCpf(request.cpf())).thenReturn(Optional.empty());
        when(personService.searchByEmail(request.email())).thenReturn(Optional.empty());

        assertThrows(
                EmptyAddresses.class,
                () -> useCase.execute(request));

        verify(personService, never()).save(any());
    }
}