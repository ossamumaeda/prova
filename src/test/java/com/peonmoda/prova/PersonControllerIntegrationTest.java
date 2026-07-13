package com.peonmoda.prova;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peonmoda.prova.dto.request.CreatePersonRequest;
import com.peonmoda.prova.dto.request.UpdatePersonRequest;
import com.peonmoda.prova.factory.PersonFactory;
import com.peonmoda.prova.repository.AddressRepository;
import com.peonmoda.prova.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = ProvaApplication.class)
@AutoConfigureMockMvc
@Testcontainers
class PersonControllerIntegrationTest {

        @Container
        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
                        .withDatabaseName("persondb")
                        .withUsername("person")
                        .withPassword("person");

        @DynamicPropertySource
        static void configure(DynamicPropertyRegistry registry) {
                registry.add("spring.datasource.url", postgres::getJdbcUrl);
                registry.add("spring.datasource.username", postgres::getUsername);
                registry.add("spring.datasource.password", postgres::getPassword);
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

        @Test
        void shouldCreatePersonSuccessfully() throws Exception {

                CreatePersonRequest request = PersonFactory.createRequest();

                mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andDo(print())
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.nome").value(request.nome()))
                                .andExpect(jsonPath("$.cpf").value(request.cpf()))
                                .andExpect(jsonPath("$.email").value(request.email()))
                                .andExpect(jsonPath("$.telefone").value(request.telefone()))
                                .andExpect(jsonPath("$.enderecos", hasSize(1)));

                assertEquals(1, personRepository.count());
                assertEquals(1, addressRepository.count());
        }

        @Test
        void shouldReturnConflictWhenCpfAlreadyExists() throws Exception {

                CreatePersonRequest request = PersonFactory.createRequest();

                mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)));

                mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isConflict());

                assertEquals(1, personRepository.count());
        }

        @Test
        void shouldReturnConflictWhenEmailAlreadyExists() throws Exception {

                CreatePersonRequest first = PersonFactory.createRequest();

                CreatePersonRequest second = new CreatePersonRequest(
                                "66040418915",
                                "Nome 2",
                                first.email(),
                                LocalDate.of(1995, 5, 10),
                                "43999999999",
                                List.of(first.enderecos().getFirst()));

                mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(first)));

                mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(second)))
                                .andExpect(status().isConflict());

                assertEquals(1, personRepository.count());
        }

        @Test
        void shouldReturnBadRequestWhenPersonHasNoAddresses() throws Exception {

                CreatePersonRequest request = PersonFactory.createRequestWithoutAddresses();

                mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest());

                assertEquals(0, personRepository.count());
        }

        @Test
        void shouldReturnBadRequestWhenCpfIsInvalid() throws Exception {

                CreatePersonRequest original = PersonFactory.createRequest();

                CreatePersonRequest request = new CreatePersonRequest(
                                original.nome(),
                                "11111111111",
                                original.email(),
                                original.dataNascimento(),
                                original.telefone(),
                                original.enderecos());

                mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest());

                assertEquals(0, personRepository.count());
        }

        @Test
        void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {

                CreatePersonRequest original = PersonFactory.createRequest();

                CreatePersonRequest request = new CreatePersonRequest(
                                original.nome(),
                                original.cpf(),
                                "email-invalido",
                                original.dataNascimento(),
                                original.telefone(),
                                original.enderecos());

                mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest());

                assertEquals(0, personRepository.count());
        }

        @Test
        void shouldReturnBadRequestWhenPhoneIsInvalid() throws Exception {

                CreatePersonRequest original = PersonFactory.createRequest();

                CreatePersonRequest request = new CreatePersonRequest(
                                original.nome(),
                                original.cpf(),
                                original.email(),
                                original.dataNascimento(),
                                "123",
                                original.enderecos());

                mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest());

                assertEquals(0, personRepository.count());
        }

        @Test
        void shouldReturnBadRequestWhenNameIsBlank() throws Exception {

                CreatePersonRequest original = PersonFactory.createRequest();

                CreatePersonRequest request = new CreatePersonRequest(
                                "",
                                original.cpf(),
                                original.email(),
                                original.dataNascimento(),
                                original.telefone(),
                                original.enderecos());

                mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isBadRequest());

                assertEquals(0, personRepository.count());
        }

        /*  GET PERSON */

        @Test
        void shouldFindPersonById() throws Exception {

                CreatePersonRequest request = PersonFactory.createRequest();

                String response = mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                UUID id = objectMapper.readTree(response)
                                .get("id")
                                .traverse(objectMapper)
                                .readValueAs(UUID.class);

                mockMvc.perform(get("/api/v1/pessoas/{id}", id))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nome")
                                                .value(request.nome()))
                                .andExpect(jsonPath("$.cpf")
                                                .value(request.cpf()));

        }

        @Test
        void shouldReturnNotFoundWhenPersonDoesNotExist() throws Exception {

                mockMvc.perform(
                                get("/api/v1/pessoas/{id}",
                                                UUID.randomUUID()))
                                .andExpect(status().isNotFound());

        }

        // @Test
        // void shouldFindAllPersons() throws Exception {

        //         CreatePersonRequest request = PersonFactory.createRequest();

        //         mockMvc.perform(post("/api/v1/pessoas")
        //                         .contentType(MediaType.APPLICATION_JSON)
        //                         .content(objectMapper.writeValueAsString(request)));

        //         mockMvc.perform(get("/api/v1/pessoas"))
        //                         .andExpect(status().isOk())
        //                         .andExpect(jsonPath("$",
        //                                         hasSize(1)));

        // }

        /* UPDATE PERSON */

        @Test
        void shouldUpdatePersonSuccessfully() throws Exception {

                CreatePersonRequest create = PersonFactory.createRequest();

                String response = mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(create)))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                UUID id = objectMapper.readTree(response)
                                .get("id")
                                .traverse(objectMapper)
                                .readValueAs(UUID.class);

                UpdatePersonRequest update = PersonFactory.updateRequest();

                mockMvc.perform(
                                patch("/api/v1/pessoas/{id}", id)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(update)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.nome")
                                                .value(update.nome()));

        }

        /* DELETE PERSON */
        @Test
        void shouldDeletePersonSuccessfully() throws Exception {

                CreatePersonRequest request = PersonFactory.createRequest();

                String response = mockMvc.perform(post("/api/v1/pessoas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                UUID id = objectMapper.readTree(response)
                                .get("id")
                                .traverse(objectMapper)
                                .readValueAs(UUID.class);

                mockMvc.perform(delete("/api/v1/pessoas/{id}", id))
                                .andExpect(status().isNoContent());

                assertEquals(
                                false,
                                personRepository.findById(id)
                                                .get()
                                                .getAtivo());

        }

        @Test
        void shouldReturnNotFoundWhenDeletingUnknownPerson() throws Exception {

                mockMvc.perform(
                                delete("/api/v1/pessoas/{id}",
                                                UUID.randomUUID()))
                                .andExpect(status().isNotFound());

        }
}