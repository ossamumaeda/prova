package com.peonmoda.prova.controller;

import com.peonmoda.prova.dto.request.CreatePersonRequest;
import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.usecase.CreatePersonUsecase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/pessoas")
@RequiredArgsConstructor
public class PersonController {

    private final CreatePersonUsecase createPersonUseCase;

    @PostMapping
    public ResponseEntity<PersonResponse> create(
            @Valid @RequestBody CreatePersonRequest request) {

        PersonResponse response = createPersonUseCase.execute(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }



}