CREATE TABLE academia (
    id BIGSERIAL PRIMARY KEY,
    cnpj VARCHAR(14) NOT NULL UNIQUE,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(500) NOT NULL,
    responsavel VARCHAR(255) NOT NULL,
    id_usuario_responsavel BIGINT,
    ativo BOOLEAN NOT NULL DEFAULT true,
    data_inclusao TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome_usuario VARCHAR(50),
    total_alunos INTEGER DEFAULT 0,
    total_professores INTEGER DEFAULT 0,
    telefone VARCHAR(20),
    email VARCHAR(255),
    CONSTRAINT fk_academia_usuario FOREIGN KEY (id_usuario_responsavel) 
        REFERENCES usuario(id) ON DELETE SET NULL
);

CREATE INDEX idx_academia_cnpj ON academia(cnpj);
CREATE INDEX idx_academia_ativo ON academia(ativo);
CREATE INDEX idx_academia_id_usuario_responsavel ON academia(id_usuario_responsavel);
