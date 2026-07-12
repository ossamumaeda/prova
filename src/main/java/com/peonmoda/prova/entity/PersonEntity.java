package com.peonmoda.prova.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pessoa", uniqueConstraints = {
        @UniqueConstraint(name = "uk_pessoa_cpf", columnNames = "cpf"),
        @UniqueConstraint(name = "uk_pessoa_email", columnNames = "email")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonEntity extends BaseEntity{

    @NotBlank
    @Column(nullable = false, length = 11)
    private String cpf;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Email
    @NotBlank
    @Column(nullable = false)
    private String email;

    @Past
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @NotBlank
    @Column(nullable = false)
    private String telefone;

    @Builder.Default
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> enderecos = new ArrayList<>();

    /**
     * Mantém o relacionamento bidirecional consistente.
     */
    public void adicionarEndereco(AddressEntity endereco) {
        endereco.setPessoa(this);
        this.enderecos.add(endereco);
    }

    public void removerEndereco(AddressEntity endereco) {
        endereco.setPessoa(null);
        this.enderecos.remove(endereco);
    }

}