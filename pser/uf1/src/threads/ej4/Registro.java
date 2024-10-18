package threads.ej4;

import java.time.LocalDateTime;

public class Registro
{
    public enum TipoRegistro
    {
        ENTRADA, SALIDA
    }
    
    private final TipoRegistro tipo;
    private final Conductor conductor;
    private final LocalDateTime fechaHora;
    
    public Registro(TipoRegistro tipo, Conductor conductor)
    {
        this.tipo = tipo;
        this.conductor = conductor;
        this.fechaHora = LocalDateTime.now();
    }

    @Override
    public String toString()
    {
        return "El conductor " + conductor.getNumero() + " ha " 
                + (tipo == TipoRegistro.ENTRADA ? "entrado" : "salido") + " del aparcamiento a las " + fechaHora;
    }
}
