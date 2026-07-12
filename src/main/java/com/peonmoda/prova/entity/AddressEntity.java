package com.peonmoda.prova.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "endereco")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumTipoEndereco tipo;

    @Column(length = 8)
    private String codigoPostal;

    @NotBlank
    @Column(nullable = false)
    private String logradouro;

    @NotBlank
    @Column(nullable = false)
    private String numero;

    private String complemento;

    @NotBlank
    @Column(nullable = false)
    private String bairro;

    @NotBlank
    @Column(nullable = false)
    private String municipio;

    @NotBlank
    @Column(nullable = false, length = 2)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(name = "fk_endereco_pessoa"))
    private PersonEntity pessoa;

    @Builder.Default
    @Column(nullable = false)
    private Boolean ativo = true;
}