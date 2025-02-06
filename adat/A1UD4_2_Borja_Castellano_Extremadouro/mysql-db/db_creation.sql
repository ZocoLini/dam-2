create table empregado
(
    Nome          varchar(25) not null,
    Apelido1      varchar(25) not null,
    Apelido2      varchar(25) not null,
    NSS           varchar(15) not null,
    Salario       float       not null,
    DataNacemento date        not null,
    Sexo          char(1)     not null,
    Rua           varchar(30) null,
    CP            char(5)     null,
    Localidad     varchar(30) null,
    Provincia     varchar(30) null,
    constraint PK_EMPREGADO primary key (NSS)
);

create table aficion
(
    NSS_Empregado varchar(15) not null,
    Aficion       varchar(50) not null,
    constraint PK_AFICION primary key (NSS_Empregado, Aficion),
    constraint FK_AFICION_EMPREGADO foreign key (NSS_Empregado) references empregado (NSS)
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
    constraint PK_TELEFONO primary key (Numero)
);

create table Departamento
(
    NumDepartamento  int(11)     not null,
    NomeDepartamento varchar(25) not null,
    constraint PK_DEPARTAMENTO primary key (NumDepartamento),
    constraint UQ_NOME_DEPARTAMENTO unique (NomeDepartamento)
);

create table lugar
(
    id               int not null,
    Num_Departamento int not null,
    Lugar            varchar(15),
    constraint PK_LUGAR primary key (id),
    constraint FK_LUGAR_DEPARTAMENTO foreign key (Num_Departamento) references Departamento (NumDepartamento),
    constraint UQ_LUGAR_DEPARTAMENTO unique (Num_Departamento, Lugar)
);

create table horas_extras
(
    NSS_Empregado varchar(15) not null,
    Data          date        not null,
    Horas         float       not null,
    constraint PK_HORAS_EXTRAS primary key (NSS_Empregado, Data),
    constraint FK_HORAS_EXTRAS_EMPREGADO foreign key (NSS_Empregado) references empregado (NSS)
);

create table Proxecto
(
    NumProxecto  int(11)     not null,
    NomeProxecto varchar(25) not null,
    Lugar        varchar(25) not null,
    constraint PK_PROXECTO primary key (NumProxecto),
    constraint UQ_NOME_PROXECTO unique (NomeProxecto)
);