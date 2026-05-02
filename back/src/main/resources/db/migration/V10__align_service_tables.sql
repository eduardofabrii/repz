ALTER TABLE personal
    ADD COLUMN IF NOT EXISTS id_academia BIGINT;

ALTER TABLE checkin
    DROP CONSTRAINT IF EXISTS fk_checkin_aluno;

ALTER TABLE checkin
    ADD COLUMN IF NOT EXISTS id_academia BIGINT;

ALTER TABLE checkin
    ADD COLUMN IF NOT EXISTS id_personal BIGINT;

ALTER TABLE avaliacao_fisica
    DROP CONSTRAINT IF EXISTS fk_avaliacao_aluno;

ALTER TABLE avaliacao_fisica
    ADD COLUMN IF NOT EXISTS id_usuario BIGINT;

ALTER TABLE avaliacao_fisica
    ADD COLUMN IF NOT EXISTS id_academia BIGINT;

ALTER TABLE avaliacao_fisica
    ADD COLUMN IF NOT EXISTS data_avaliacao TIMESTAMP;

ALTER TABLE avaliacao_fisica
    ADD COLUMN IF NOT EXISTS imc NUMERIC(8,2);

ALTER TABLE avaliacao_fisica
    ADD COLUMN IF NOT EXISTS percentual_gordura NUMERIC(5,2);

ALTER TABLE avaliacao_fisica
    ADD COLUMN IF NOT EXISTS medidas VARCHAR(1000);

ALTER TABLE avaliacao_fisica
    ALTER COLUMN id_aluno DROP NOT NULL;

CREATE TABLE IF NOT EXISTS plano (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    duracao_dias INTEGER NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    academia_id BIGINT NOT NULL,
    data_inclusao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_alteracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome_usuario VARCHAR(50)
);
