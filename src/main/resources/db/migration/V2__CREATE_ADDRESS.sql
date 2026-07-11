CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE endereco (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    pessoa_id UUID NOT NULL,

    tipo VARCHAR(20) NOT NULL,
    codigo_postal VARCHAR(8),
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(255),
    bairro VARCHAR(255) NOT NULL,
    municipio VARCHAR(255) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_endereco_pessoa
        FOREIGN KEY (pessoa_id)
        REFERENCES pessoa(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_endereco_tipo
        CHECK (tipo IN ('PRINCIPAL', 'ENTREGA', 'COMERCIAL'))
);

CREATE INDEX idx_endereco_pessoa
    ON endereco(pessoa_id);

CREATE INDEX idx_endereco_municipio
    ON endereco(municipio);