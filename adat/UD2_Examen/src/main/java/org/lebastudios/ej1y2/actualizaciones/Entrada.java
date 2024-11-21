package org.lebastudios.ej1y2.actualizaciones;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Entrada
{
    private String numero;
    private Rio rio;
    private String fecha;
    private Datos datos;
}
