create database clinica_veterinaria;

use clinica_veterinaria;

-- Creación de tablas
CREATE TABLE Propietario
(
    dni   VARCHAR(15) PRIMARY KEY,
    nome  VARCHAR(50),
    ap1   VARCHAR(50),
    ap2   VARCHAR(50),
    tlf   VARCHAR(50),
    eMail VARCHAR(50)
);

CREATE TABLE Raza
(
    codRaza     INT(11) PRIMARY KEY,
    descripcion VARCHAR(40)
);

CREATE TABLE Perro
(
    chip           VARCHAR(20) PRIMARY KEY,
    nome           VARCHAR(50),
    codRaza        INT(11),
    dniPropietario VARCHAR(15),
    FOREIGN KEY (codRaza) REFERENCES Raza (codRaza),
    FOREIGN KEY (dniPropietario) REFERENCES Propietario (dni)
);

CREATE TABLE Operacion
(
    codOperacion INT(11) AUTO_INCREMENT PRIMARY KEY,
    chip         VARCHAR(20),
    descripcion  TEXT,
    data         DATE,
    anestesia    TINYINT(4),
    raios        TINYINT(4),
    sangre       TINYINT(4),
    scaner       TINYINT(4),
    FOREIGN KEY (chip) REFERENCES Perro (chip)
);

CREATE TABLE Vacuna
(
    codVacina     INT(11) PRIMARY KEY AUTO_INCREMENT,
    nomeVacina    VARCHAR(50),
    numTotalDoses INT(11)
);

CREATE TABLE Perro_Vacuna
(
    codVacinacion INT(11) PRIMARY KEY AUTO_INCREMENT,
    chip          VARCHAR(20),
    codVacina     INT(11),
    data          DATE,
    observacions  VARCHAR(100),
    dose          INT(11),
    FOREIGN KEY (chip) REFERENCES Perro (chip),
    FOREIGN KEY (codVacina) REFERENCES Vacuna (codVacina)
);