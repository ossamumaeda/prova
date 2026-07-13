package com.peonmoda.prova.controller;

import com.peonmoda.prova.dto.ErrorResponse;
import com.peonmoda.prova.dto.request.CreateAddressRequest;
import com.peonmoda.prova.dto.request.UpdateAddressRequest;
import com.peonmoda.prova.dto.response.AddressResponse;
import com.peonmoda.prova.usecase.UpdateAddressUseCase;
import com.peonmoda.prova.usecase.address.CreateAddressUseCase;
import com.peonmoda.prova.usecase.address.DeleteAddressUseCase;
import com.peonmoda.prova.usecase.address.GetAddressByIdUseCase;
import com.peonmoda.prova.usecase.address.ListAddressesUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Address", description = "Operations related to addresses")
public class AddressController {

        private final CreateAddressUseCase createAddressUseCase;
        private final UpdateAddressUseCase updateAddressUseCase;
        private final DeleteAddressUseCase deleteAddressUseCase;
        private final GetAddressByIdUseCase getAddressByIdUseCase;
        private final ListAddressesUseCase listAddressesUseCase;

        @Operation(summary = "Create new address")
        @PostMapping
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "Address created"),
                        @ApiResponse(responseCode = "409", description = "Request dto fields with problem", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        public ResponseEntity<AddressResponse> create(
                        @PathVariable UUID personId,
                        @Valid @RequestBody CreateAddressRequest request) throws NotFoundException {

                AddressResponse response = createAddressUseCase.execute(personId, request);

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(response);
        }

        @Operation(summary = "Update address")
        @PatchMapping("/{addressId}")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Address updated"),
                        @ApiResponse(responseCode = "409", description = "Request dto fields with problem", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        public ResponseEntity<AddressResponse> update(
                        @Parameter(description = "Person identifier UUID", example = "bb3d8dc4-c8e5-47e0-aef8-5ca8b5d7db54") @PathVariable UUID personId,
                        @Parameter(description = "Address identifier UUID", example = "bb3d8dc4-c8e5-47e0-aef8-5ca8b5d7db54") @PathVariable UUID addressId,
                        @Valid @RequestBody UpdateAddressRequest request) {

                return ResponseEntity.ok(
                                updateAddressUseCase.execute(personId, addressId, request));
        }

        @Operation(summary = "Find all person addresses")
        @GetMapping
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Addresses list") })
        public ResponseEntity<List<AddressResponse>> list(
                        @Parameter(description = "Person identifier UUID", example = "bb3d8dc4-c8e5-47e0-aef8-5ca8b5d7db54") @PathVariable UUID personId)
                        throws NotFoundException {

                return ResponseEntity.ok(
                                listAddressesUseCase.execute(personId));
        }

        @Operation(summary = "Find address by id")
        @GetMapping("/{addressId}")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Address info"),
                        @ApiResponse(responseCode = "409", description = "Address id not found or not related to person", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        public ResponseEntity<AddressResponse> get(
                        @Parameter(description = "Person identifier UUID", example = "bb3d8dc4-c8e5-47e0-aef8-5ca8b5d7db54") @PathVariable UUID personId,
                        @Parameter(description = "Address identifier UUID", example = "bb3d8dc4-c8e5-47e0-aef8-5ca8b5d7db54") @PathVariable UUID addressId) {

                return ResponseEntity.ok(
                                getAddressByIdUseCase.execute(personId, addressId));
        }

        @Operation(summary = "Deactivate address")
        @DeleteMapping("/{addressId}")
        @ApiResponses({
                        @ApiResponse(responseCode = "204", description = ""),
                        @ApiResponse(responseCode = "409", description = "Address id not found or not related to person", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
        })
        public ResponseEntity<Void> delete(
                        @Parameter(description = "Person identifier UUID", example = "bb3d8dc4-c8e5-47e0-aef8-5ca8b5d7db54") @PathVariable UUID personId,
                        @Parameter(description = "Address identifier UUID", example = "bb3d8dc4-c8e5-47e0-aef8-5ca8b5d7db54") @PathVariable UUID addressId)
                        throws NotFoundException {
                deleteAddressUseCase.execute(personId, addressId);

                return ResponseEntity.noContent().build();
        }

}