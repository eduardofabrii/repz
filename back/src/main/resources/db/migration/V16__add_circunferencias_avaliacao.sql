-- Circunferências estruturadas da avaliação física (RF27).
-- Aditiva e idempotente, no mesmo estilo da V10/V15.

ALTER TABLE avaliacao_fisica
    ADD COLUMN IF NOT EXISTS cintura_cm NUMERIC(6,2);

ALTER TABLE avaliacao_fisica
    ADD COLUMN IF NOT EXISTS quadril_cm NUMERIC(6,2);

ALTER TABLE avaliacao_fisica
    ADD COLUMN IF NOT EXISTS braco_cm NUMERIC(6,2);

ALTER TABLE avaliacao_fisica
    ADD COLUMN IF NOT EXISTS coxa_cm NUMERIC(6,2);
