package com.peonmoda.prova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peonmoda.prova.entity.PersonEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {

    Optional<PersonEntity> findByCpf(String cpf);

    Optional<PersonEntity> findByEmail(String email);

    Optional<PersonEntity> findByIdAndAtivoTrue(UUID id);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByCpfAndAtivoTrue(String cpf);

    boolean existsByEmailAndAtivoTrue(String email);
}
