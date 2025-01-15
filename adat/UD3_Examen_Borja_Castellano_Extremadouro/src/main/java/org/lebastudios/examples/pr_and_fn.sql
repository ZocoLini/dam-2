create procedure pr_existeEmpleadoParaFamiliar(
    @nssEmpleado varchar(15),
    @nssFamiliar varchar(15),
    @existe bit output
) as
begin
    declare countResult int = (select count(*)
                               from Familiar f
                               where f.NSS = @nssFamiliar
                                 and f.NSS_Empregado = @nssEmpleado);

    if countResult > 0
        begin
            set @existe = 1;
        end
    else
        begin
            set @existe = 0;
        end
end

go

create function fn_obtenerIdFamiliar() returns int as
begin
    declare @idFamiliar int = (select max(IdFamiliar) + 1
                               from Familiar);

    if @idFamiliar is null set @idFamiliar = 1;

    return @idFamiliar;
end