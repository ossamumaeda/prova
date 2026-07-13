package com.peonmoda.prova.mapper;

import org.springframework.stereotype.Component;

import com.peonmoda.prova.dto.request.CreatePersonRequest;
import com.peonmoda.prova.dto.request.UpdatePersonRequest;
import com.peonmoda.prova.dto.response.AddressResponse;
import com.peonmoda.prova.dto.response.PersonResponse;
import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.entity.PersonEntity;

@Component
public class PersonMapper {

    public PersonEntity converterParaEntidade(CreatePersonRequest dto) {

        PersonEntity person = new PersonEntity();

        person.setCpf(dto.cpf());
        person.setNome(dto.nome());
        person.setEmail(dto.email());
        person.setTelefone(dto.telefone());
        person.setDataNascimento(dto.dataNascimento());

        dto.enderecos().forEach(addressDto -> {

            AddressEntity address = new AddressEntity();

            address.setTipo(addressDto.tipo());
            address.setCodigoPostal(addressDto.codigoPostal());
            address.setLogradouro(addressDto.logradouro());
            address.setNumero(addressDto.numero());
            address.setComplemento(addressDto.complemento());
            address.setBairro(addressDto.bairro());
            address.setMunicipio(addressDto.municipio());
            address.setEstado(addressDto.estado());

            person.adicionarEndereco(address);

        });

        return person;
    }

    public PersonResponse converterParaResponse(PersonEntity entity) {

        return new PersonResponse(
                entity.getId(),
                entity.getCpf(),
                entity.getNome(),
                entity.getEmail(),
                entity.getDataNascimento(),
                entity.getTelefone(),
                entity.getEnderecos()
                        .stream()
                        .map(address -> new AddressResponse(
                                address.getId(),
                                address.getTipo(),
                                address.getCodigoPostal(),
                                address.getLogradouro(),
                                address.getNumero(),
                                address.getComplemento(),
                                address.getBairro(),
                                address.getMunicipio(),
                                address.getEstado()))
                        .toList());
    }

    public PersonEntity updatePerson(
            UpdatePersonRequest dto,
            PersonEntity person) {

        person.setNome(dto.nome());
        person.setEmail(dto.email());
        person.setTelefone(dto.telefone());
        person.setDataNascimento(dto.dataNascimento());

        return person;

    }
}
