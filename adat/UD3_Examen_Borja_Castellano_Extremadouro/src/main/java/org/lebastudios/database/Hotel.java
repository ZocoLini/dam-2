package org.lebastudios.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Hotel extends Alojamiento
{
    private short codHotel;
    private byte estrellas;
    private short hotelSede;

    public Hotel(String nombre, String direccion, String localidad, String telefono, float precioHabitacion,
            float camaExtra, byte numHabitaciones, byte estrellas, short hotelSede)
    {
        super(nombre, direccion, localidad, telefono, precioHabitacion, camaExtra, numHabitaciones);

        this.estrellas = estrellas;
        this.hotelSede = hotelSede;
    }

    public Hotel(short codigo, String nombre, String direccion, String localidad, String telefono,
            float precioHabitacion,
            float camaExtra, byte numHabitaciones, byte estrellas, short hotelSede)
    {
        super(codigo, nombre, direccion, localidad, telefono, precioHabitacion, camaExtra, numHabitaciones);
        this.codHotel = codigo;
        this.estrellas = estrellas;
        this.hotelSede = hotelSede;
    }
}
