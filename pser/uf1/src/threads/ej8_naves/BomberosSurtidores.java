package threads.ej8_naves;

public class BomberosSurtidores extends Nave
{
    @Override
    protected void act()
    {
        Meteorito meteorito;

        while ((meteorito = CentralHWWC.getInstance().obtenerMeteoritoTaladrado()) != null)
        {
            if (meteorito.estaBombaColocada()) continue;

            synchronized (meteorito)
            {
                if (!meteorito.estaTaladrado()) continue;
                meteorito.colocarBomba();
                System.out.println("Bomba colocada y combustible repostado");
                meteorito.notifyAll();
            }
        }
    }
}
