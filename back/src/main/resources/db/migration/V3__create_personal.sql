CREATE TABLE personal (
    id BIGSERIAL PRIMARY KEY,
    especialidades VARCHAR(255),
    ativo BOOLEAN DEFAULT true,

    id_usuario BIGINT NOT NULL,

    data_inclusao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome_usuario VARCHAR(50),

    CONSTRAINT fk_personal_usuario 
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id)
);