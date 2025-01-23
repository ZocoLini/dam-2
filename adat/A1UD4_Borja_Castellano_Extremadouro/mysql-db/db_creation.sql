create table empregado
(
    Nome          varchar(25) not null,
    Apelido1      varchar(25) not null,
    Apelido2      varchar(25) not null,
    NSS           varchar(15) not null,
    Salario       float       not null,
    DataNacemento date        not null,
    Sexo          char(1)     not null,
    constraint PK_EMPREGADO primary key (NSS)
);

create table Departamento
(
    NumDepartamento  int(11)     not null,
    NomeDepartamento varchar(25) not null,
    constraint PK_DEPARTAMENTO primary key (NumDepartamento)
);

create table Proxecto
(
    NumProxecto  int(11)     not null,
    NomeProxecto varchar(25) not null,
    Lugar        varchar(25) not null,
    constraint PK_PROXECTO primary key (NumProxecto)
);