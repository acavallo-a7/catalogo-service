CREATE TABLE categorie (
    id       BIGSERIAL PRIMARY KEY,
    prefisso VARCHAR(2) UNIQUE,
    nome     VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO categorie (prefisso, nome) VALUES
    ('EL', 'Elettronica'),
    ('AB', 'Abbigliamento'),
    ('CA', 'Casa e Giardino'),
    ('SP', 'Sport e Tempo Libero'),
    ('LI', 'Libri e Cancelleria'),
    ('GI', 'Giocattoli e Prima Infanzia'),
    ('AL', 'Alimentari e Bevande'),
    (NULL, 'Non Categorizzato');
