CREATE TABLE pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    cpf VARCHAR(14) UNIQUE NOT NULL,
    cep CHAR(10) NOT NULL,
    logradouro VARCHAR(255),
    bairro VARCHAR(100),
    municipio VARCHAR(100),
    estado CHAR(2),
    numero CHAR(10),
    complemento VARCHAR(255)
);
