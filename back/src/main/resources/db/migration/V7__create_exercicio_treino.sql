CREATE TABLE exercicio_treino (
    id BIGSERIAL PRIMARY KEY,
    nome_exercicio VARCHAR(100),
    series INTEGER,
    repeticoes VARCHAR(50),
    carga_kg NUMERIC(10,2),
    ordem INTEGER,

    id_treino BIGINT NOT NULL,

    data_inclusao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome_usuario VARCHAR(50),

    CONSTRAINT fk_exercicio_treino 
        FOREIGN KEY (id_treino)
        REFERENCES treino(id)
);