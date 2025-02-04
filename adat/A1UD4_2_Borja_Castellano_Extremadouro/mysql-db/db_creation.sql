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

create table familiar
(
    NSS_Empregado varchar(15) not null,
    Numero        int         not null,
    NSS           varchar(15) not null,
    Nome          varchar(25) not null,
    Apelido1      varchar(25) not null,
    Apelido2      varchar(25) not null,
    DataNacemento date        not null,
    Parentesco    varchar(20) not null,
    Sexo          char(1)     not null,
    constraint PK_FAMILIAR primary key (NSS, Numero),
    constraint FK_FAMILIAR_EMPREGADO foreign key (NSS_Empregado) references empregado (NSS),
    constraint UQ_FAMILIAR_NSS unique (NSS)
);

create table telefonos
(
    NSS    varchar(15) not null,
    Numero varchar(9)  not null,
    Info   varchar(15) null,
    constraint FK_TELEFONO_EMPREGADO foreign key (NSS) references empregado (NSS),
    constraint PK_TELEFONO primary key (Numero),
    constraint CK_TELEFONO_NUMEROS check ( Numero like '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
);

create table Departamento
(
    NumDepartamento  int(11)     not null,
    NomeDepartamento varchar(25) not null,
    constraint PK_DEPARTAMENTO primary key (NumDepartamento),
    constraint UQ_NOME_DEPARTAMENTO unique (NomeDepartamento)
);

create table Proxecto
(
    NumProxecto  int(11)     not null,
    NomeProxecto varchar(25) not null,
    Lugar        varchar(25) not null,
    constraint PK_PROXECTO primary key (NumProxecto),
    constraint UQ_NOME_PROXECTO unique (NomeProxecto)
);