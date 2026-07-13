package com.peonmoda.prova.person;

import com.peonmoda.prova.dto.request.UpdatePersonAggregateRequest;
import com.peonmoda.prova.dto.request.UpdatePersonRequest;
import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.exception.DuplicateEmailException;
import com.peonmoda.prova.factory.AddressFactory;
import com.peonmoda.prova.factory.PersonFactory;
import com.peonmoda.prova.mapper.AddressMapper;
import com.peonmoda.prova.mapper.PersonMapper;
import com.peonmoda.prova.service.AddressService;
import com.peonmoda.prova.service.PersonService;
import com.peonmoda.prova.usecase.UpdatePersonAggregateUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePersonAggregateUseCaseTest {

    @Mock
    private PersonService personService;

    @Mock
    private AddressService addressService;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private UpdatePersonAggregateUseCase useCase;

    @Test
    void shouldUpdatePersonWithoutAddresses() throws Exception {

        UUID personId = UUID.randomUUID();

        PersonEntity person = PersonFactory.createEntity();
        person.setId(personId);

        UpdatePersonRequest updatePerson = PersonFactory.updateRequest();

        UpdatePersonAggregateRequest request =
                new UpdatePersonAggregateRequest(
                        updatePerson,
                        List.of()
                );

        PersonResponse response = PersonFactory.createResponse();

        when(personService.searchById(personId)).thenReturn(person);
        // when(personService.searchByEmail(updatePerson.email())).thenReturn(Optional.empty());
        when(personService.save(person)).thenReturn(person);
        when(personMapper.converterParaResponse(person)).thenReturn(response);

        PersonResponse result = useCase.execute(personId, request);

        assertNotNull(result);

        verify(personMapper).updatePerson(updatePerson, person);
        verify(personService).save(person);

        verifyNoInteractions(addressService);
        verifyNoInteractions(addressMapper);
    }

    @Test
    void shouldUpdatePersonAndAddresses() throws Exception {

        UUID personId = UUID.randomUUID();

        PersonEntity person = PersonFactory.createEntity();
        person.setId(personId);

        AddressEntity address = AddressFactory.createEntity();

        UpdatePersonRequest updatePerson = PersonFactory.updateRequest();

        UpdatePersonAggregateRequest request =
                new UpdatePersonAggregateRequest(
                        updatePerson,
                        List.of(
                                AddressFactory.updateRequest(address.getId())
                        )
                );

        PersonResponse response = PersonFactory.createResponse();

        when(personService.searchById(personId)).thenReturn(person);
        // when(personService.searchByEmail(updatePerson.email())).thenReturn(Optional.empty());
        when(personService.save(person)).thenReturn(person);

        when(addressService.searchById(address.getId()))
                .thenReturn(address);

        when(personMapper.converterParaResponse(person))
                .thenReturn(response);

        PersonResponse result = useCase.execute(personId, request);

        assertNotNull(result);

        verify(personMapper).updatePerson(updatePerson, person);
        verify(addressMapper).updateAddress(request.enderecos().get(0), address);
        verify(addressService).searchById(address.getId());
    }

    @Test
    void shouldThrowDuplicateEmailException() throws NotFoundException {

        UUID personId = UUID.randomUUID();

        PersonEntity person = PersonFactory.createEntity();
        person.setId(personId);

        PersonEntity existing = PersonFactory.createEntity();
        existing.setId(UUID.randomUUID());

        UpdatePersonRequest updatePerson = PersonFactory.updateRequest();

        UpdatePersonAggregateRequest request =
                new UpdatePersonAggregateRequest(
                        updatePerson,
                        List.of()
                );

        when(personService.searchById(personId))
                .thenReturn(person);

        when(personService.searchByEmail(updatePerson.email()))
                .thenReturn(Optional.of(existing));

        assertThrows(
                DuplicateEmailException.class,
                () -> useCase.execute(personId, request)
        );

        verify(personService, never()).save(any());
        verifyNoInteractions(addressService);
    }


}