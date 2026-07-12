package com.peonmoda.prova.controller;

import com.peonmoda.prova.dto.request.CreateAddressRequest;
import com.peonmoda.prova.dto.request.UpdateAddressRequest;
import com.peonmoda.prova.dto.response.AddressResponse;
import com.peonmoda.prova.usecase.UpdateAddressUseCase;
import com.peonmoda.prova.usecase.address.CreateAddressUseCase;
import com.peonmoda.prova.usecase.address.DeleteAddressUseCase;
import com.peonmoda.prova.usecase.address.GetAddressByIdUseCase;
import com.peonmoda.prova.usecase.address.ListAddressesUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pessoas/{personId}/enderecos")
@RequiredArgsConstructor
public class AddressController {

    private final CreateAddressUseCase createAddressUseCase;
    private final UpdateAddressUseCase updateAddressUseCase;
    private final DeleteAddressUseCase deleteAddressUseCase;
    private final GetAddressByIdUseCase getAddressByIdUseCase;
    private final ListAddressesUseCase listAddressesUseCase;

    @PostMapping
    public ResponseEntity<AddressResponse> create(
            @PathVariable UUID personId,
            @Valid @RequestBody CreateAddressRequest request) throws NotFoundException {

        AddressResponse response =
                createAddressUseCase.execute(personId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<AddressResponse> update(
            @PathVariable UUID personId,
            @PathVariable UUID addressId,
            @Valid @RequestBody UpdateAddressRequest request) {

        return ResponseEntity.ok(
                updateAddressUseCase.execute(personId, addressId, request)
        );
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> list(
            @PathVariable UUID personId) throws NotFoundException {

        return ResponseEntity.ok(
                listAddressesUseCase.execute(personId)
        );
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> get(
            @PathVariable UUID personId,
            @PathVariable UUID addressId) {

        return ResponseEntity.ok(
                getAddressByIdUseCase.execute(personId, addressId)
        );
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID personId,
            @PathVariable UUID addressId) throws NotFoundException {
        System.out.println(personId);
        System.out.println(addressId);
        deleteAddressUseCase.execute(personId, addressId);

        return ResponseEntity.noContent().build();
    }

}