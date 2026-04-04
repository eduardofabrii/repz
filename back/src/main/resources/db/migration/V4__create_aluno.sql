CREATE TABLE aluno (
    id BIGSERIAL PRIMARY KEY,
    data_inicio DATE,
    objetivo VARCHAR(255),

    id_usuario BIGINT NOT NULL,
    id_academia BIGINT NOT NULL,

    data_inclusao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome_usuario VARCHAR(50),

    CONSTRAINT fk_aluno_usuario 
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id),

    CONSTRAINT fk_aluno_academia 
        FOREIGN KEY (id_academia)
        REFERENCES academia(id)
);