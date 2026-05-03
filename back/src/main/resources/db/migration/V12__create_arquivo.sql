CREATE TABLE arquivo (
    id           BIGSERIAL    PRIMARY KEY,
    user_id      BIGINT       NOT NULL,
    file_name    VARCHAR(500) NOT NULL,
    url          TEXT,
    data_inclusao TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_arquivo_usuario FOREIGN KEY (user_id) REFERENCES usuario(id)
);
