package threads.ej8_naves;

import java.util.function.Consumer;

public class Meteorito
{
    public boolean estaBombaColocada() 
    {
        return estado == EstadoMeteorito.BOMBA_COLACADA;
    }

    public boolean esTaladrabe() {
        return estado == EstadoMeteorito.DEFAULT;
    }

    private enum EstadoMeteorito
    {
        DEFAULT,
        TALADRADO,
        DESTRUIDO,
        BOMBA_COLACADA
    }
    private EstadoMeteorito estado = EstadoMeteorito.DEFAULT;
    private Consumer<Meteorito> onDestruccion;

    public Meteorito(Consumer<Meteorito> onDestruccion)
    {
        this.onDestruccion = onDestruccion;
    }
    
    public boolean estaTaladrado()
    {
        return estado == EstadoMeteorito.TALADRADO;
    }

    public synchronized void taladrar()
    {
        if (estado != EstadoMeteorito.DEFAULT) throw new IllegalStateException("El meteorito no se puede taladrar");
        
        estado = EstadoMeteorito.TALADRADO;
    }
    
    public void colocarBomba()
    {
        estado = EstadoMeteorito.BOMBA_COLACADA;
        
        new Thread(() ->
        {
            try
            {
                Thread.sleep(5000);
                estado = EstadoMeteorito.DESTRUIDO;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            
            onDestruccion.accept(this);
        }).start();
    }
}
