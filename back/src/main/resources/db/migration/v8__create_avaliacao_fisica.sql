CREATE TABLE avaliacao_fisica (
    id BIGSERIAL PRIMARY KEY,
    peso_kg NUMERIC(5,2),
    altura_cm NUMERIC(5,2),

    id_aluno BIGINT NOT NULL,
    id_personal BIGINT,

    data_inclusao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome_usuario VARCHAR(50),

    CONSTRAINT fk_avaliacao_aluno 
        FOREIGN KEY (id_aluno)
        REFERENCES aluno(id),

    CONSTRAINT fk_avaliacao_personal 
        FOREIGN KEY (id_personal)
        REFERENCES personal(id)
);