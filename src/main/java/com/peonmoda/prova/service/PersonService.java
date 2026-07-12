package com.peonmoda.prova.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.peonmoda.prova.entity.PersonEntity;
import com.peonmoda.prova.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService {

    private final PersonRepository repository;

    public List<PersonEntity> getAll(){
        return repository.findAll();
    }

    public PersonEntity save(PersonEntity pessoa) {
        return repository.save(pessoa);
    }

    public PersonEntity update(PersonEntity pessoa) {
        return repository.save(pessoa);
    }

    public PersonEntity searchById(UUID id) throws NotFoundException {
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new NotFoundException());
    }

    public Optional<PersonEntity> searchByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public Optional<PersonEntity> searchByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<PersonEntity> listPeople() {

        return repository.findAll()
                .stream()
                .filter(PersonEntity::getAtivo)
                .toList();
    }

    public void remove(UUID id) throws NotFoundException {

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