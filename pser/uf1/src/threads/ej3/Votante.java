package threads.ej3;

public class Votante extends Thread
{
    private static final int TIME_TO_WAIT = 500;
    
    private final Partido[] partidos;

    public Votante(Partido[] partidos)
    {
        this.partidos = partidos;
    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep((int) (Math.random() * TIME_TO_WAIT));
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        int voto = (int) (Math.random() * partidos.length);
        partidos[voto].increment();
    }
}
