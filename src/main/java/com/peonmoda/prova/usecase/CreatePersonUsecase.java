package com.peonmoda.prova.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.peonmoda.prova.dto.request.CreateAddressRequest;
import com.peonmoda.prova.dto.request.CreatePersonRequest;
import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.exception.DuplicateCpfException;
import com.peonmoda.prova.exception.DuplicateEmailException;
import com.peonmoda.prova.exception.InvalidPersonException;
import com.peonmoda.prova.mapper.PersonMapper;
import com.peonmoda.prova.service.PersonService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePersonUsecase {

    private final PersonService personService;
    private final PersonMapper mapper;

    public PersonResponse execute(CreatePersonRequest dto) {

        validarCpf(dto.cpf());

        validarEmail(dto.email());

        validarEnderecos(dto.enderecos());

        // PersonEntity person = converterParaEntidade(dto);
        PersonEntity person = mapper.converterParaEntidade(dto);

        person.getEnderecos()
                .forEach(endereco -> endereco.setPessoa(person));

        PersonEntity saved = personService.save(person);

        return mapper.converterParaResponse(saved);
    }

    private void validarCpf(String cpf) {

        if (personService.searchByCpf(cpf).isPresent()) {
            throw new DuplicateCpfException(cpf);
        }

    }

    private void validarEmail(String email) {

        if (personService.searchByEmail(email).isPresent()) {
            throw new DuplicateEmailException(email);
        }

    }

    private void validarEnderecos(List<CreateAddressRequest> enderecos) {

        if (enderecos == null || enderecos.isEmpty()) {
            throw new InvalidPersonException(
                    "A pessoa deve possuir pelo menos um endereço.");
        }

    }

}
