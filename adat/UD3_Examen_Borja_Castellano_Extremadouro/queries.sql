select codigo, nombre, direccion, localidad, telefono, precio_habitacion, cama_extra, numhabitaciones, 
       cod_hotel, estrellas, hotelsede 
from ALOJAMIENTO a 
    inner join HOTEL h on a.codigo = h.cod_hotel 