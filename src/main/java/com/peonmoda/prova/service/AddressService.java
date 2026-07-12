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

    public AddressEntity salvar(AddressEntity endereco) {
        return repository.save(endereco);
    }

    public AddressEntity atualizar(AddressEntity endereco) {
        return repository.save(endereco);
    }

    @Transactional(readOnly = true)
    public AddressEntity buscarPorId(UUID id) {

        return repository.findById(id)
                .filter(AddressEntity::getAtivo)
                .orElseThrow(() ->
                        new EntityNotFoundException("Endereço não encontrado."));
    }

    @Transactional(readOnly = true)
    public List<AddressEntity> listar() {

        return repository.findAll()
                .stream()
                .filter(AddressEntity::getAtivo)
                .toList();
    }

    public void remover(UUID id) {

        AddressEntity endereco = buscarPorId(id);

        endereco.setAtivo(false);

        repository.save(endereco);
    }

}