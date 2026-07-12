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

    public PersonEntity save(PersonEntity pessoa) {
        return repository.save(pessoa);
    }

    public PersonEntity update(PersonEntity pessoa) {
        return repository.save(pessoa);
    }

    @Transactional(readOnly = true)
    public PersonEntity searchById(UUID id) {

        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada."));
    }

    @Transactional(readOnly = true)
    public PersonEntity searchByCpf(String cpf) {
        return repository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada."));
    }

    @Transactional(readOnly = true)
    public PersonEntity searchByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada."));
    }

    @Transactional(readOnly = true)
    public List<PersonEntity> listPeople() {

        return repository.findAll()
                .stream()
                .filter(PersonEntity::getAtivo)
                .toList();
    }

    public void remove(UUID id) {

        PersonEntity pessoa = searchById(id);

        pessoa.setAtivo(false);

        repository.save(pessoa);
    }

    public boolean existCpf(String cpf) {
        return repository.existsByCpfAndAtivoTrue(cpf);
    }

    public boolean existEmail(String email) {
        return repository.existsByEmailAndAtivoTrue(email);
    }

}