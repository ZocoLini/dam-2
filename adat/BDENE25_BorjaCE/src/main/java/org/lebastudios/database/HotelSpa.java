package org.lebastudios.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HotelSpa extends Hotel
{
    private short codSpa;
    private char gorro;
    private byte capacidad;

    public HotelSpa(String nombre, String direccion, String localidad, String telefono, float precioHabitacion,
            float camaExtra, byte numHabitaciones, byte estrellas, short hotelSede, char gorro, byte capacidad)
    {
        super(nombre, direccion, localidad, telefono, precioHabitacion, camaExtra, numHabitaciones, estrellas, hotelSede);

        this.gorro = gorro;
        this.capacidad = capacidad;
    }

    public HotelSpa(short codigo, String nombre, String direccion, String localidad, String telefono,
            float precioHabitacion, float camaExtra, byte numHabitaciones, byte estrellas, short hotelSede, char gorro,
            byte capacidad)
    {
        super(codigo, nombre, direccion, localidad, telefono, precioHabitacion, camaExtra, numHabitaciones, estrellas,
                hotelSede);
        this.codSpa = codigo;
        this.gorro = gorro;
        this.capacidad = capacidad;
    }
}
