package com.peonmoda.prova.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdatePersonAggregateRequest(

        @NotNull
        @Valid
        UpdatePersonRequest pessoa,

        @Valid
        @NotNull
        List<UpdateAddressRequest> enderecos

) {
}
