package threads.ej3;

import java.util.concurrent.atomic.AtomicInteger;

public class Votante extends Thread
{
    private final AtomicInteger[] partidos;

    public Votante(AtomicInteger[] partidos)
    {
        this.partidos = partidos;
    }

    @Override
    public void run()
    {
        try
        {
            Thread.sleep((int) (Math.random() * 500));
        } catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }

        int voto = (int) (Math.random() * partidos.length);
        partidos[voto].incrementAndGet();
    }
}
