create table Lugar(
    Nome_lugar varchar(35),
    Num_departamento,
    constraint PK_LUGAR primary key (Nome_lugar),
    constraint FK_LUGAR_DEPARTAMENTO foreign key departamento (Num_departamento)
)