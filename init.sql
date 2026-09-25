CREATE DATABASE cadastro_carreta;

USE cadastro_carreta;

-- Tabela para armazenar os dados dos proprietários/motoristas
CREATE TABLE proprietarios (
    id_proprietario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf_cnpj VARCHAR(18) NOT NULL UNIQUE,
    telefone VARCHAR(15),
    email VARCHAR(100)
);

-- Tabela para armazenar as informações do cavalo mecânico (trator)
CREATE TABLE cavalos (
    id_cavalo INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(8) NOT NULL UNIQUE,
    renavam VARCHAR(11) NOT NULL UNIQUE,
    modelo VARCHAR(50),
    marca VARCHAR(50),
    ano_fabricacao INT,
    id_proprietario INT,
    FOREIGN KEY (id_proprietario) REFERENCES proprietarios(id_proprietario)
);

-- Tabela para armazenar as carretas (reboques/semirreboques)
CREATE TABLE carretas (
    id_carreta INT AUTO_INCREMENT PRIMARY KEY,
    placa VARCHAR(8) NOT NULL UNIQUE,
    renavam VARCHAR(11) NOT NULL UNIQUE,
    tipo VARCHAR(50) NOT NULL, -- Ex: Baú, Sider, Graneleiro, Prancha
    eixos INT NOT NULL,
    capacidade_carga_kg DECIMAL(10, 2),
    ano_fabricacao INT,
    id_proprietario INT,
    FOREIGN KEY (id_proprietario) REFERENCES proprietarios(id_proprietario)
);

-- Tabela para vincular qual carreta está engatada em qual cavalo (histórico de acoplamento)
CREATE TABLE acoplamentos (
    id_acoplamento INT AUTO_INCREMENT PRIMARY KEY,
    id_cavalo INT NOT NULL,
    id_carreta INT NOT NULL,
    data_engate DATETIME NOT NULL,
    data_desengate DATETIME,
    FOREIGN KEY (id_cavalo) REFERENCES cavalos(id_cavalo),
    FOREIGN KEY (id_carreta) REFERENCES carretas(id_carreta)
);