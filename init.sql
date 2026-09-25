-- Criar tabelas para o banco de dados do PostgreSQL

CREATE TABLE IF NOT EXISTS proprietarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf_cnpj VARCHAR(18) NOT NULL UNIQUE,
    telefone VARCHAR(15),
    email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS cavalos (
    id  SERIAL PRIMARY KEY,
    placa VARCHAR(8) NOT NULL UNIQUE,
    renavam VARCHAR(11) NOT NULL UNIQUE,
    modelo VARCHAR(50),
    marca VARCHAR(50),
    ano_fabricacao INT,
    id_proprietario INT REFERENCES proprietarios(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS carretas (
    id SERIAL PRIMARY KEY,
    placa VARCHAR(8) NOT NULL UNIQUE,
    renavam VARCHAR(11) NOT NULL UNIQUE,
    tipo VARCHAR(50) NOT NULL,
    eixos INT NOT NULL,
    capacidade_carga_kg DECIMAL(10, 2),
    ano_fabricacao INT,
    id_proprietario INT REFERENCES proprietarios(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS acoplamentos (
    id_acoplamento SERIAL PRIMARY KEY,
    id_cavalo INT NOT NULL REFERENCES cavalos(id) ON DELETE CASCADE,
    id_carreta INT NOT NULL REFERENCES carretas(id) ON DELETE CASCADE,
    data_engate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_desengate TIMESTAMP
);