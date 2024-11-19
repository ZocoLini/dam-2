package threads.ej8_naves;

public class Meteorito
{
    private enum EstadoMeteorito
    {
        DEFAULT,
        TALADRADO,
        DESTRUIDO,
        BOMBA_COLACADA
    }

    private final int identificador;
    private EstadoMeteorito estado = EstadoMeteorito.DEFAULT;

    public Meteorito(int identificador)
    {
        this.identificador = identificador;
    }

    public int getIdentificador()
    {
        return identificador;
    }

    public boolean estaBombaColocada() 
    {
        return estado == EstadoMeteorito.BOMBA_COLACADA;
    }
    public boolean esTaladrabe() {
        return estado == EstadoMeteorito.DEFAULT;
    }

    public void taladrar()
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

            System.out.println("Meteorito #" + identificador + ": Meteorito destruido");
        }).start();
    }
}
