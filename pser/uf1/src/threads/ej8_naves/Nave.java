package threads.ej8_naves;

public abstract class Nave extends Thread
{
    @Override
    public final void run()
    {
        act();
        
        CentralHWWC.getInstance().aterrizar();
    }
    
    protected abstract void act();
}
