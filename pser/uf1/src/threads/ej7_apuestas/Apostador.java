package threads.ej7_apuestas;

public class Apostador extends Thread
{
    private final static int NUM_APUESTAS = 5;
    
    @Override
    public void run()
    {
        try
        {
            for (int i = 0; i < NUM_APUESTAS; i++) 
            {
                sleep((int) (Math.random() * 200) + 100);
                
                Porra.getInstance().apostar(
                        new Apuesta(i, TipoApuesta.values()[(int) (Math.random() * 3)], this)
                );
            }
        }
        catch (InterruptedException e) {throw new RuntimeException(e);}
    }
}
