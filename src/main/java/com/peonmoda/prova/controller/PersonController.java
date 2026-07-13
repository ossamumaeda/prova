package com.peonmoda.prova.controller;

import com.peonmoda.prova.dto.ErrorResponse;
import com.peonmoda.prova.dto.request.CreatePersonRequest;
import com.peonmoda.prova.dto.request.UpdatePersonAggregateRequest;
import com.peonmoda.prova.dto.request.UpdatePersonRequest;
import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.usecase.person.CreatePersonUsecase;
import com.peonmoda.prova.usecase.person.DeletePersonUseCase;
import com.peonmoda.prova.usecase.person.GetPersonByIdUseCase;
import com.peonmoda.prova.usecase.person.ListPeopleUsecase;
import com.peonmoda.prova.usecase.person.UpdatePersonAggregateUseCase;
import com.peonmoda.prova.usecase.person.UpdatePersonUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Person", description = "Operations related to people")
public class PersonController {

    private final GetPersonByIdUseCase getPersonByIdUseCase;
    private final CreatePersonUsecase createPersonUseCase;
    private final UpdatePersonUseCase updatePersonUseCase;
    private final UpdatePersonAggregateUseCase updatePersonAggregateUseCase;
    private final DeletePersonUseCase deletePersonUseCase;
    private final ListPeopleUsecase listPeopleUsecase;

    @Operation(summary = "Create new person with address")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Person created"),
            @ApiResponse(responseCode = "409", description = "Request dto fields with problem", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<PersonResponse> create(
            @Valid @RequestBody CreatePersonRequest request) {

        PersonResponse response = createPersonUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "Find all people")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of people"),
    })
    @GetMapping("")
    public ResponseEntity<List<PersonResponse>> findAll() throws NotFoundException {

        return ResponseEntity.ok(
                listPeopleUsecase.execute());
    }

    @Operation(summary = "Find person by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person info"),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> findById(
            @Parameter(description = "Person identifier UUID", example = "bb3d8dc4-c8e5-47e0-aef8-5ca8b5d7db54") @PathVariable UUID id)
            throws NotFoundException {

        return ResponseEntity.ok(
                getPersonByIdUseCase.execute(id));
    }

    @Operation(summary = "Update person")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated Person info"),
            @ApiResponse(responseCode = "409", description = "Request dto fields with problem", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePerson(
            @Parameter(description = "Person identifier UUID", example = "bb3d8dc4-c8e5-47e0-aef8-5ca8b5d7db54") @PathVariable UUID id,
            @Valid @RequestBody UpdatePersonRequest request) throws NotFoundException {
        PersonResponse updated = updatePersonUseCase.execute(id, request);

        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Update person and address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated Person info"),
            @ApiResponse(responseCode = "409", description = "Request dto fields with problem", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> updateAggregate(
            @Parameter(description = "Person identifier UUID", example = "bb3d8dc4-c8e5-47e0-aef8-5ca8b5d7db54") @PathVariable UUID id,
            @Valid @RequestBody UpdatePersonAggregateRequest request) throws NotFoundException {
        return ResponseEntity.ok(
                updatePersonAggregateUseCase.execute(id, request));
    }

    @Operation(summary = "Deactivate person")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated Person info"),
            @ApiResponse(responseCode = "404", description = "Person not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Person identifier UUID", example = "bb3d8dc4-c8e5-47e0-aef8-5ca8b5d7db54") @PathVariable UUID id)
            throws NotFoundException {

        deletePersonUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }

}