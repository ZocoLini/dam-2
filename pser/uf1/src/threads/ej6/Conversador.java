package threads.ej6;

import java.io.IOException;

public class Conversador extends Thread
{
    private final Conversacion conversacion;
    private final String nombre;
    
    public Conversador(Conversacion conversacion, String nombre)
    {
        this.conversacion = conversacion;
        this.nombre = nombre;
    }
    
    @Override
    public void run()
    {
        try
        {
            while (!conversacion.isOver())
            {
                conversacion.conversar(nombre);
            }
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
