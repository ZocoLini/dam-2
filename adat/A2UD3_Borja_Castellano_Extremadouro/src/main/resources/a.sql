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