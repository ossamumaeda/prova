package com.peonmoda.prova;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.peonmoda.prova.dto.request.CreateAddressRequest;
import com.peonmoda.prova.dto.request.CreatePersonRequest;
import com.peonmoda.prova.dto.request.UpdateAddressRequest;

import com.peonmoda.prova.factory.AddressFactory;
import com.peonmoda.prova.factory.PersonFactory;

import com.peonmoda.prova.repository.AddressRepository;
import com.peonmoda.prova.repository.PersonRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.springframework.test.web.servlet.MockMvc;


import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.util.UUID;


import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class AddressControllerIntegrationTest {


    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16-alpine")
                    .withDatabaseName("persondb")
                    .withUsername("person")
                    .withPassword("person");



    @DynamicPropertySource
    static void configureDatabase(
            DynamicPropertyRegistry registry) {


        registry.add(
                "spring.datasource.url",
                postgres::getJdbcUrl
        );


        registry.add(
                "spring.datasource.username",
                postgres::getUsername
        );


        registry.add(
                "spring.datasource.password",
                postgres::getPassword
        );

    }



    @Autowired
    private MockMvc mockMvc;



    @Autowired
    private ObjectMapper objectMapper;



    @Autowired
    private PersonRepository personRepository;



    @Autowired
    private AddressRepository addressRepository;



    @BeforeEach
    void cleanDatabase() {

        addressRepository.deleteAll();
        personRepository.deleteAll();

    }



    private UUID createPerson() throws Exception {


        CreatePersonRequest request =
                PersonFactory.createRequest();



        String response =
                mockMvc.perform(post("/api/v1/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        ))
                .andReturn()
                .getResponse()
                .getContentAsString();



        JsonNode json =
                objectMapper.readTree(response);



        return UUID.fromString(
                json.get("id")
                        .asText()
        );

    }




    private UUID createAddress(UUID personId)
            throws Exception {


        CreateAddressRequest request =
                AddressFactory.createRequest();



        String response =
                mockMvc.perform(post(
                        "/api/v1/pessoas/{personId}/enderecos",
                        personId)

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        ))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();



        JsonNode json =
                objectMapper.readTree(response);



        return UUID.fromString(
                json.get("id")
                        .asText()
        );

    }





    @Test
    void shouldCreateAddressSuccessfully()
            throws Exception {


        UUID personId =
                createPerson();


        CreateAddressRequest request =
                AddressFactory.createRequest();



        mockMvc.perform(post(
                "/api/v1/pessoas/{personId}/enderecos",
                personId)

                .contentType(MediaType.APPLICATION_JSON)

                .content(
                    objectMapper.writeValueAsString(request)
                ))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.logradouro")
                .value(request.logradouro()));



        assertEquals(
                2,
                addressRepository.count()
        );

    }





    @Test
    void shouldGetAddressById()
            throws Exception {


        UUID personId =
                createPerson();


        UUID addressId =
                createAddress(personId);



        mockMvc.perform(get(
                "/api/v1/pessoas/{personId}/enderecos/{addressId}",
                personId,
                addressId))

        .andExpect(status().isOk())

        .andExpect(jsonPath("$.id")
                .value(addressId.toString()));

    }





    @Test
    void shouldListAddresses()
            throws Exception {


        UUID personId =
                createPerson();



        createAddress(personId);
        createAddress(personId);



        mockMvc.perform(get(
                "/api/v1/pessoas/{personId}/enderecos",
                personId))

        .andExpect(status().isOk())

        .andExpect(jsonPath("$",
                hasSize(3)));

    }

    @Test
    void shouldUpdateAddressSuccessfully()
            throws Exception {

        UUID personId =
                createPerson();

        UUID addressId =
                createAddress(personId);

        UpdateAddressRequest request =
                AddressFactory.updateRequest(addressId);

        mockMvc.perform(patch(
                "/api/v1/pessoas/{personId}/enderecos/{addressId}",
                personId,
                addressId)

                .contentType(MediaType.APPLICATION_JSON)

                .content(
                    objectMapper.writeValueAsString(request)
                ))

        .andExpect(status().isOk())

        .andExpect(jsonPath("$.logradouro")
                .value(request.logradouro()));

    }

    @Test
    void shouldDeleteAddressSuccessfully()
            throws Exception {


        UUID personId =
                createPerson();



        UUID addressId =
                createAddress(personId);



        mockMvc.perform(delete(
                "/api/v1/pessoas/{personId}/enderecos/{addressId}",
                personId,
                addressId))

        .andExpect(status().isNoContent());



        assertEquals(
                false,
                addressRepository.findById(addressId)
                        .get()
                        .getAtivo()
        );

    }





    @Test
    void shouldReturnNotFoundWhenPersonDoesNotExist()
            throws Exception {


        mockMvc.perform(get(
                "/api/v1/pessoas/{personId}/enderecos",
                UUID.randomUUID()))

        .andExpect(status().isNotFound());

    }





    @Test
    void shouldReturnNotFoundWhenAddressDoesNotExist()
            throws Exception {


        UUID personId =
                createPerson();



        mockMvc.perform(get(
                "/api/v1/pessoas/{personId}/enderecos/{addressId}",
                personId,
                UUID.randomUUID()))

        .andExpect(status().isNotFound());

    }


}