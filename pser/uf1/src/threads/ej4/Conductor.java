package threads.ej4;

public class Conductor extends Thread
{
    private final int numero;
    
    public Conductor(int numero)
    {
        this.numero = numero;
    }
    
    @Override
    public synchronized void run()
    {
        int tiempoSeg = (int) (Math.random() * 5) + 1;
        
        try
        {
            while (!Aparcamiento.getInstance().intentarAparcar(this))
            {
                wait();
            }
            
            sleep(tiempoSeg);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        
        Aparcamiento.getInstance().salir(this);
    }

    public int getNumero()
    {
        return numero;
    }
}