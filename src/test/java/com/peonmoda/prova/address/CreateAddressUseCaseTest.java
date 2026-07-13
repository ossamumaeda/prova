package com.peonmoda.prova.address;

import com.peonmoda.prova.dto.request.CreateAddressRequest;
import com.peonmoda.prova.dto.response.AddressResponse;
import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.factory.AddressFactory;
import com.peonmoda.prova.factory.PersonFactory;
import com.peonmoda.prova.mapper.AddressMapper;
import com.peonmoda.prova.service.AddressService;
import com.peonmoda.prova.service.PersonService;
import com.peonmoda.prova.usecase.address.CreateAddressUseCase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAddressUseCaseTest {

    @Mock
    private PersonService personService;

    @Mock
    private AddressService addressService;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private CreateAddressUseCase useCase;

    @Test
    void shouldCreateAddressSuccessfully() throws NotFoundException {

        UUID personId = UUID.randomUUID();

        PersonEntity person = PersonFactory.createEntity();
        AddressEntity address = AddressFactory.createEntity();
        CreateAddressRequest request = AddressFactory.createRequest();
        AddressResponse response = AddressFactory.createResponse();

        when(personService.searchById(personId)).thenReturn(person);
        when(addressMapper.toEntity(request)).thenReturn(address);
        when(addressService.save(address)).thenReturn(address);
        when(addressMapper.toResponse(address)).thenReturn(response);

        AddressResponse result = useCase.execute(personId, request);

        assertNotNull(result);

        ArgumentCaptor<AddressEntity> captor = ArgumentCaptor.forClass(AddressEntity.class);

        verify(addressService).save(captor.capture());

        AddressEntity saved = captor.getValue();

        assertEquals(person, saved.getPessoa());

        verify(personService).searchById(personId);
        verify(addressMapper).toEntity(request);
        verify(addressMapper).toResponse(address);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenPersonDoesNotExist() throws NotFoundException {

        UUID personId = UUID.randomUUID();
        CreateAddressRequest request = AddressFactory.createRequest();

        when(personService.searchById(personId))
                .thenThrow(new NotFoundException());

        assertThrows(
                NotFoundException.class,
                () -> useCase.execute(personId, request)
        );

        verify(addressService, never()).save(any());
        verifyNoInteractions(addressMapper);
    }

}