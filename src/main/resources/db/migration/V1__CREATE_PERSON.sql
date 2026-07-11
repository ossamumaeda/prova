CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE pessoa (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    cpf VARCHAR(11) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    data_nascimento DATE,
    telefone VARCHAR(20) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT uk_pessoa_cpf UNIQUE (cpf),
    CONSTRAINT uk_pessoa_email UNIQUE (email)
);

CREATE INDEX idx_pessoa_nome
    ON pessoa(nome);