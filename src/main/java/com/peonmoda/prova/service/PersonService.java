package com.peonmoda.prova.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.repository.PersonRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService {

    private final PersonRepository repository;

    public PersonEntity salvar(PersonEntity pessoa) {
        return repository.save(pessoa);
    }

    public PersonEntity atualizar(PersonEntity pessoa) {
        return repository.save(pessoa);
    }

    @Transactional(readOnly = true)
    public PersonEntity buscarPorId(UUID id) {

        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Pessoa não encontrada."));
    }

    @Transactional(readOnly = true)
    public List<PersonEntity> listar() {

        return repository.findAll()
                .stream()
                .filter(PersonEntity::getAtivo)
                .toList();
    }

    public void remover(UUID id) {

        PersonEntity pessoa = buscarPorId(id);

        pessoa.setAtivo(false);

        repository.save(pessoa);
    }

    public boolean existeCpf(String cpf) {
        return repository.existsByCpfAndAtivoTrue(cpf);
    }

    public boolean existeEmail(String email) {
        return repository.existsByEmailAndAtivoTrue(email);
    }

}