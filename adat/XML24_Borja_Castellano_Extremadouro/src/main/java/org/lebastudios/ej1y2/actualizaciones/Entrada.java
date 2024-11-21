package org.lebastudios.ej1y2.actualizaciones;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor
public class Entrada
{
    private String numero;
    private Rio rio;
    private LocalDate fecha;
    private Datos datos;


    public String getFechaFormatoMedicionesRios()
    {
        return DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(fecha);
    }
}
