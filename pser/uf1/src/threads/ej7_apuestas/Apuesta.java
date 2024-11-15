package threads.ej7_apuestas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Apuesta
{
    private int cantidad;
    private TipoApuesta tipoApuesta;
    private Apostador apostador;
}
