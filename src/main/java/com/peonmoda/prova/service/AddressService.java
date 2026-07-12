package com.peonmoda.prova.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peonmoda.prova.entity.AddressEntity;
import com.peonmoda.prova.repository.AddressRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository repository;

    public AddressEntity save(AddressEntity endereco) {
        return repository.save(endereco);
    }

    public AddressEntity update(AddressEntity endereco) {
        return repository.save(endereco);
    }

    @Transactional(readOnly = true)
    public AddressEntity searchById(UUID id) {
        return repository.findById(id)
                .filter(AddressEntity::getAtivo)
                .orElseThrow(() ->
                        new EntityNotFoundException("Endereço não encontrado."));
    }

    @Transactional(readOnly = true)
    public List<AddressEntity> listAdresses() {

        return repository.findAll()
                .stream()
                .filter(AddressEntity::getAtivo)
                .toList();
    }

    public void remove(UUID id) {

        AddressEntity endereco = searchById(id);

        endereco.setAtivo(false);

        repository.save(endereco);
    }

}