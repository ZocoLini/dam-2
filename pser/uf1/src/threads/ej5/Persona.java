package threads.ej5;

public abstract class Persona extends Thread
{
    protected String nombre;

    public Persona(String nombre)
    {
        this.nombre = nombre;
    }

    @Override
    public final void run()
    {
        esperar();

        empezarJornada();
    }
    
    protected abstract void empezarJornada();
    
    private void esperar()
    {
        try
        {
            sleep((long) (Math.random() * 5000));
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
