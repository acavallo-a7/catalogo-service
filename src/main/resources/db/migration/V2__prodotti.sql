CREATE TABLE prodotti (
    id           BIGSERIAL PRIMARY KEY,
    nome         VARCHAR(255) NOT NULL,
    sku          VARCHAR(255) NOT NULL UNIQUE,
    prezzo       NUMERIC(10,2) NOT NULL,
    disponibile  BOOLEAN NOT NULL,
    produttore   VARCHAR(255) NOT NULL,
    ean          VARCHAR(255),
    categoria_id BIGINT NOT NULL REFERENCES categorie(id)
);
