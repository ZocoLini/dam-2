package org.lebastudios.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Alojamiento
{
    private short codigo;
    private String nombre;
    private String direccion;
    private String localidad;
    private String telefono;
    private float precioHabitacion;
    private float camaExtra;
    private byte numHabitaciones;

    public Alojamiento(String nombre, String direccion, String localidad, String telefono, float precioHabitacion,
            float camaExtra, byte numHabitaciones)
    {
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
        this.telefono = telefono;
        this.precioHabitacion = precioHabitacion;
        this.camaExtra = camaExtra;
        this.numHabitaciones = numHabitaciones;
    }
}
