create function fn_getAlojaminetoPorNombre(
    @nombreAlojamineto varchar(40)
) returns smallint as
begin
    declare @codigoAlojamineto smallint = (select codigo from ALOJAMIENTO a where @nombreAlojamineto = a.nombre);
    return @codigoAlojamineto
end

go

create procedure pr_showActividadesInfo(
    @nombreAlojamiento varchar(40),
) as
begin
    
end