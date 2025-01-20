package org.lebastudios.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CasaRural extends Alojamiento
{
    private short codCasa;
    private char alquilerCompleta;

    public CasaRural(String nombre, String direccion, String localidad, String telefono, float precioHabitacion,
            float camaExtra, byte numHabitaciones, char alquilerCompleta)
    {
        super(nombre, direccion, localidad, telefono, precioHabitacion, camaExtra, numHabitaciones);
        
        this.alquilerCompleta = alquilerCompleta;
    }

    public CasaRural(short codigo, String nombre, String direccion, String localidad, String telefono,
            float precioHabitacion, float camaExtra, byte numHabitaciones, char alquilerCompleta)
    {
        super(codigo, nombre, direccion, localidad, telefono, precioHabitacion, camaExtra, numHabitaciones);
        this.codCasa = codigo;
        this.alquilerCompleta = alquilerCompleta;
    }
}
