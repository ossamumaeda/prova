package com.peonmoda.prova.factory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.peonmoda.prova.dto.request.CreateAddressRequest;
import com.peonmoda.prova.dto.request.CreatePersonRequest;
import com.peonmoda.prova.dto.request.UpdatePersonRequest;
import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.entity.PersonEntity;

public class PersonFactory {

    public static CreatePersonRequest createRequest() {
        return new CreatePersonRequest(
                "12330487916",
                "Gabriel Maeda",
                "gabriel@email.com",
                LocalDate.of(2000, 1, 1),
                "43988150821",
                List.of(AddressFactory.createRequest()));
    }

    public static PersonResponse createResponse() {
        return new PersonResponse(
                UUID.randomUUID(),
                "12330487916",
                "12330487916",
                "gabriel@email.com",
                LocalDate.of(2000, 1, 1),
                "44999999999",
                List.of(AddressFactory.createResponse()));
    }

    public static PersonEntity createEntity() {

        PersonEntity person = new PersonEntity();

        person.setId(UUID.randomUUID());
        person.setNome("Gabriel Maeda");
        person.setCpf("12330487916");
        person.setEmail("gabriel@email.com");
        person.setTelefone("44999999999");
        person.setDataNascimento(LocalDate.of(2000, 1, 1));

        return person;
    }

    public static CreatePersonRequest createRequestWithoutAddresses() {
        return new CreatePersonRequest(
                "12330487916",
                "12330487916",
                "gabriel@email.com",
                LocalDate.of(2000, 1, 1),
                "44999999999",
                new ArrayList<CreateAddressRequest>());
    }

    public static PersonEntity createEntityWithoutAddresses() {
        PersonEntity person = createEntity();
        person.setEnderecos(new ArrayList<>());
        return person;
    }

    public static UpdatePersonRequest updateRequest() {
        return new UpdatePersonRequest(
                "Gabriel Atualizado",
                "gabriel@email.com",
                LocalDate.of(2000, 1, 1),
                "44999999999");
    }

}
