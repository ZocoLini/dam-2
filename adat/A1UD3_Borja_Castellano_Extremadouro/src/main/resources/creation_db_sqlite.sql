PRAGMA foreign_keys = ON;

drop table if exists EMPREGADO;

create table EMPREGADO
(

    Nome                       varchar(25) not null,
    Apelido_1                  varchar(25) not null,
    Apelido_2                  varchar(25) not null,
    NSS                        varchar(15) not null,
    Rua                        varchar(30) null,
    Numero_rua                 int         null,
    Piso                       varchar(4)  null,
    CP                         char(5)     null,
    Localidade                 varchar(25) null,
    Data_nacemento             date        null,
    Salario                    float       null,
    Sexo                       char(1)     null,
    NSS_Supervisa              varchar(15) null,
    Num_departamento_pertenece int         null,
    constraint PK_Empregado_NSS primary key (NSS),
    constraint FK_EMPREGADO_EMPREGADO foreign key (NSS_Supervisa) references EMPREGADO (NSS),
    constraint FK_EMPREGADO_DEPARTAMENTO foreign key (Num_departamento_pertenece) references DEPARTAMENTO (Num_departamento)
);

drop table if exists DEPARTAMENTO;

create table DEPARTAMENTO
(

    Num_departamento  int         not null,
    Nome_departamento varchar(25) not null,
    NSS_dirige        varchar(15) null,
    Data_direccion    date        null,
    constraint PK_Departamento_Num_departamento primary key (Num_departamento),
    constraint U_Departamento_Nome_departamento unique (Nome_departamento),
    constraint FK_DEPARTAMENTO_EMPREGADO foreign key (NSS_dirige) references EMPREGADO (NSS)
);

drop table if exists PROXECTO;

create table PROXECTO
(

    Num_proxecto              int         not null,
    Nome_proxecto             varchar(25) not null,
    Lugar                     varchar(25) not null,
    Num_departamento_controla int         not null,
    constraint PK_Proyecto_Num_proxecto primary key (Num_proxecto),
    constraint U_Proxecto_Nome_proxecto unique (Nome_proxecto),
    constraint FK_PROXECTO_DEPARTAMENTO foreign key (Num_departamento_controla) references DEPARTAMENTO (Num_departamento)
);

drop table if exists EMPREGADO_PROXECTO;

create table EMPREGADO_PROXECTO
(

    NSS_empregado  varchar(15) not null,
    Num_proxecto   int         not null,
    Horas_semanais int         null,
    constraint PK_EMPREGADO_PROXECTO_NSS_empregadoNum_proxecto primary key (NSS_empregado, Num_proxecto),
    constraint FK_EMPREGADO_PROXECTO_EMPREGADO foreign key (NSS_empregado) references EMPREGADO (NSS),
    constraint FK_EMPREGADO_PROXECTO_PROXECTO foreign key (Num_proxecto) references PROXECTO (Num_proxecto)
);
PRAGMA foreign_keys = OFF;