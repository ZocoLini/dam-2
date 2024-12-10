update EMPREGADO
set salario = (select Salario) + 26000
where Num_departamento_pertenece = (select Num_departamento
                                    from DEPARTAMENTO
                                    where Nome_departamento = 'PERSOAL');

insert into DEPARTAMENTO (Num_departamento, Nome_departamento, NSS_dirige, Data_direccion)
VALUES (?, ?, ?, ?);

delete
from EMPREGADO_PROXECTO
where NSS_empregado = ?
  and Num_proxecto = ?;

select e1.Nome,
       e1.Apelido_1,
       e1.Apelido_2,
       e1.Localidade,
       e1.Salario,
       e1.Data_nacemento,
       e2.Nome,
       DEPARTAMENTO.Nome_departamento
from EMPREGADO e1
         inner join EMPREGADO e2 on e1.NSS = e2.NSS
         inner join DEPARTAMENTO on e1.Num_departamento_pertenece = DEPARTAMENTO.Num_departamento
where e1.Localidade = ?;

update PROXECTO
set Num_departamento_controla = (select Num_departamento from DEPARTAMENTO where Nome_departamento = ?)
where Nome_proxecto = ?;

insert into PROXECTO (Num_proxecto, Nome_proxecto, Lugar, Num_departamento_controla)
values (?, ?, ?, ?);

delete
from EMPREGADO_PROXECTO
where Num_proxecto = ?;
delete
from PROXECTO
where Num_proxecto = ?;

select Num_proxecto, num_proxecto, nome_proxecto, lugar, num_departamento_controla
from PROXECTO
where Num_departamento_controla = (select Num_departamento from DEPARTAMENTO where Nome_departamento = ?)

create proc pr_cambioDomicilio(@nss varchar, @rua varchar, @numero_rua int, @piso varchar, @cp int,
                               @localidade varchar) as
begin
    update EMPREGADO
    set Rua        = @rua,
        Numero_rua = @numero_rua,
        Piso       = @piso,
        CP         = @cp,
        Localidade = @localidade
    where NSS = @nss
end
go
create proc pr_DatosProxecto(@num_proxecto int, @nombre varchar output, @lugar varchar output,
                             @num_dep int output) as
begin
    set @nombre = (select Nome_proxecto from PROXECTO where Num_proxecto = @num_proxecto)
    set @lugar = (select Lugar from PROXECTO where  Num_proxecto = @num_proxecto)
    set @num_dep = (select Num_departamento_controla from PROXECTO where  Num_proxecto = @num_proxecto)
end
go
create proc pr_DepartControlaProxe(@num_min_proxec int) as
begin

end
go
create function fn_nEmpDepart(@nome_dep varchar) returns int as
begin
    return (select count(*)
            from EMPREGADO
            where Num_departamento_pertenece =
                  (select Num_departamento from DEPARTAMENTO where Nome_departamento = @nome_dep))
end