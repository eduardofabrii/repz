CREATE TABLE treino (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100),
    divisao VARCHAR(50),
    ativo BOOLEAN DEFAULT true,
    id_personal BIGINT,
    data_inclusao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome_usuario VARCHAR(50),

    CONSTRAINT fk_treino_personal 
        FOREIGN KEY (id_personal)
        REFERENCES personal(id)
);