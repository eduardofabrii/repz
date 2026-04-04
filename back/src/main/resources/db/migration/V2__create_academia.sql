CREATE TABLE academia (
    id BIGSERIAL PRIMARY KEY,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    razao_social VARCHAR(255) NOT NULL,
    endereco VARCHAR(500) NOT NULL,

    id_usuario_responsavel BIGINT NOT NULL,

    ativo BOOLEAN NOT NULL DEFAULT true,

    data_inclusao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome_usuario VARCHAR(50),

    CONSTRAINT fk_academia_usuario 
        FOREIGN KEY (id_usuario_responsavel)
        REFERENCES usuario(id)
);