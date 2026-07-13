package com.peonmoda.prova.controller;

import com.peonmoda.prova.dto.request.CreatePersonRequest;
import com.peonmoda.prova.dto.request.UpdatePersonAggregateRequest;
import com.peonmoda.prova.dto.request.UpdatePersonRequest;
import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.usecase.CreatePersonUsecase;
import com.peonmoda.prova.usecase.DeletePersonUseCase;
import com.peonmoda.prova.usecase.GetPersonByIdUseCase;
import com.peonmoda.prova.usecase.ListPeopleUsecase;
import com.peonmoda.prova.usecase.UpdatePersonAggregateUseCase;
import com.peonmoda.prova.usecase.UpdatePersonUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pessoas")
@RequiredArgsConstructor
public class PersonController {

    private final GetPersonByIdUseCase getPersonByIdUseCase;
    private final CreatePersonUsecase createPersonUseCase;
    private final UpdatePersonUseCase updatePersonUseCase;
    private final UpdatePersonAggregateUseCase updatePersonAggregateUseCase;
    private final DeletePersonUseCase deletePersonUseCase;
    private final ListPeopleUsecase listPeopleUsecase;
    @PostMapping
    public ResponseEntity<PersonResponse> create(
            @Valid @RequestBody CreatePersonRequest request) {

        PersonResponse response = createPersonUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<PersonResponse>> findAll() throws NotFoundException {

        return ResponseEntity.ok(
                listPeopleUsecase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> findById(
            @PathVariable UUID id) throws NotFoundException {

        return ResponseEntity.ok(
             getPersonByIdUseCase.execute(id));
    }

    /**
     * Atualiza apenas os dados da pessoa.
     * @throws NotFoundException 
     */ 
    @PatchMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePerson(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePersonRequest request) throws NotFoundException {
        PersonResponse updated = updatePersonUseCase.execute(id, request);

        return ResponseEntity.ok(updated);
    }

    /**
     * Atualiza pessoa e endereços na mesma requisição.
     * @throws NotFoundException 
     */
    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> updateAggregate(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePersonAggregateRequest request) throws NotFoundException {
        return ResponseEntity.ok(
                updatePersonAggregateUseCase.execute(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) throws NotFoundException {

        deletePersonUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

}