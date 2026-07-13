package com.peonmoda.prova.address;

import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.exception.AddressNotRelatedToPersonException;
import com.peonmoda.prova.factory.AddressFactory;
import com.peonmoda.prova.factory.PersonFactory;
import com.peonmoda.prova.service.AddressService;
import com.peonmoda.prova.service.PersonService;
import com.peonmoda.prova.usecase.address.DeleteAddressUseCase;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAddressUseCaseTest {

    @Mock
    private PersonService personService;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private DeleteAddressUseCase useCase;

    @Test
    void shouldSoftDeleteAddress() throws NotFoundException {

        UUID personId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();
        UUID addressId2 = UUID.randomUUID();

        PersonEntity person = PersonFactory.createEntity();
        person.setId(personId);

        AddressEntity address = AddressFactory.createEntity();
        address.setId(addressId);
        address.setPessoa(person);

        AddressEntity address2 = AddressFactory.createEntity();
        address2.setId(addressId2);
        address2.setPessoa(person);

        person.setEnderecos(List.of(address,address2));

        when(personService.searchById(personId)).thenReturn(person);
        when(addressService.searchById(addressId)).thenReturn(address);
        
        when(addressService.save(address)).thenReturn(address);

        useCase.execute(personId, addressId);

        assertFalse(address.getAtivo());

        verify(addressService).save(address);
    }

    @Test
    void shouldThrowExceptionWhenAddressDoesNotBelongToPerson() throws NotFoundException {

        UUID personId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        PersonEntity person = PersonFactory.createEntity();
        person.setId(personId);

        PersonEntity anotherPerson = PersonFactory.createEntity();
        anotherPerson.setId(UUID.randomUUID());

        AddressEntity address = AddressFactory.createEntity();
        address.setId(addressId);
        address.setPessoa(anotherPerson);

        when(personService.searchById(personId)).thenReturn(person);
        when(addressService.searchById(addressId)).thenReturn(address);

        assertThrows(
                AddressNotRelatedToPersonException.class,
                () -> useCase.execute(personId, addressId));

        verify(addressService, never()).save(any());
    }

    @Test
    void shouldThrowNotFoundWhenPersonDoesNotExist() throws NotFoundException {

        UUID personId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        when(personService.searchById(personId))
                .thenThrow(new NotFoundException());

        assertThrows(
                NotFoundException.class,
                () -> useCase.execute(personId, addressId));

        verify(addressService, never()).searchById(any());
        verify(addressService, never()).save(any());
    }

    @Test
    void shouldThrowNotFoundWhenAddressDoesNotExist() throws NotFoundException {

        UUID personId = UUID.randomUUID();
        UUID addressId = UUID.randomUUID();

        PersonEntity person = PersonFactory.createEntity();

        when(personService.searchById(personId)).thenReturn(person);

        when(addressService.searchById(addressId))
                .thenThrow(new EntityNotFoundException());

        assertThrows(
                EntityNotFoundException.class,
                () -> useCase.execute(personId, addressId));

        verify(addressService, never()).save(any());
    }

}
