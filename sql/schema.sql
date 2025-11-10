-- Schema for Funcionarios project (MySQL)
CREATE DATABASE IF NOT EXISTS recursos_humanos;
USE recursos_humanos;

CREATE TABLE IF NOT EXISTS funcionario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_identificacion VARCHAR(20) NOT NULL,
    numero_identificacion VARCHAR(50) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    estado_civil VARCHAR(30),
    sexo CHAR(1),
    direccion VARCHAR(255),
    telefono VARCHAR(50),
    fecha_nacimiento DATE
);

CREATE TABLE IF NOT EXISTS grupo_familiar (
    id INT AUTO_INCREMENT PRIMARY KEY,
    funcionario_id INT NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    parentesco VARCHAR(50),
    fecha_nacimiento DATE,
    FOREIGN KEY (funcionario_id) REFERENCES funcionario(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS formacion_academica (
    id INT AUTO_INCREMENT PRIMARY KEY,
    funcionario_id INT NOT NULL,
    universidad VARCHAR(150),
    nivel_estudio VARCHAR(100),
    titulo VARCHAR(150),
    FOREIGN KEY (funcionario_id) REFERENCES funcionario(id) ON DELETE CASCADE
);