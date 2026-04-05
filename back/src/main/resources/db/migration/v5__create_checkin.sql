CREATE TABLE checkin (
    id BIGSERIAL PRIMARY KEY,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    id_aluno BIGINT NOT NULL,

    data_inclusao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome_usuario VARCHAR(50),

    CONSTRAINT fk_checkin_aluno 
        FOREIGN KEY (id_aluno)
        REFERENCES aluno(id)
);