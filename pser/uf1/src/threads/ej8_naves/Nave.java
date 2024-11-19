package threads.ej8_naves;

public abstract class Nave extends Thread
{
    private final int identificador;
    
    public Nave(int identificador)
    {
        this.identificador = identificador;
    }
    
    @Override
    public final void run()
    {
        try
        {
            act();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        CentralHWWC.getInstance().aterrizar();
    }

    public int getIdentificador()
    {
        return identificador;
    }

    protected abstract void act() throws InterruptedException;
}
