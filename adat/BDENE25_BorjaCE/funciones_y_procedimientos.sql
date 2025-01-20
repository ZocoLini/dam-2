create function fn_getAlojaminetoPorNombre(
    @nombreAlojamineto varchar(40)
) returns smallint as
begin
    declare @codigoAlojamineto smallint = (select codigo from ALOJAMIENTO a where @nombreAlojamineto = a.nombre);
    return @codigoAlojamineto
end

go

drop procedure pr_getHotelSede

go

create procedure pr_getHotelSede(
    @nombreHotel varchar(40),
    @nombreHotelSede varchar(40) output
) as
begin
    declare @codHotelSede smallint =
        (select h2.hotelsede
         from HOTEL h2
         where h2.cod_hotel =
               (select a2.codigo
                from ALOJAMIENTO a2
                where a2.nombre = @nombreHotel))

    if @codHotelSede is null
        begin
            set @nombreHotelSede = 'SIN SEDE';
            return;
        end

    set @nombreHotelSede =
            (select nombre
             from ALOJAMIENTO a
             where a.codigo = (select h.cod_hotel
                               from HOTEL h
                               where h.cod_hotel = @codHotelSede))
end

go

create procedure pr_showActividadesInfo(
    @nombreAlojamiento varchar(40)
) as
begin
    select act.nombre
    from ACTIVIDAD act
    where act.codigo in (select cod_actividad
                         from ALOJAMIENTO_ACTIVIDAD
                         where cod_alojamiento = (select codigo
                                                  from ALOJAMIENTO a
                                                  where a.nombre = @nombreAlojamiento))
end