package threads.ej8_naves;

public class BombarderosSurtidores extends Nave
{
    public BombarderosSurtidores(int identificador)
    {
        super(identificador);
    }

    @Override
    protected void act() throws InterruptedException
    {
        Meteorito meteorito;

        while ((meteorito = CentralHWWC.getInstance().obtenerMeteoritoTaladrado()) != null)
        {
            if (meteorito.estaBombaColocada()) continue;

            synchronized (meteorito)
            {
                System.out.println("Bombardero #" + getIdentificador() + ": Colocando bomba y repostando combustible " +
                        "en meteorito #" + meteorito.getIdentificador());
                sleep(5000);
                meteorito.colocarBomba();
                System.out.println("Bombardero #" + getIdentificador() + ": Bomba colocada y combustible repostado en " +
                        "meteorito #" + meteorito.getIdentificador());
                meteorito.notifyAll();
            }
        }

        System.out.println("Bombardero #" + getIdentificador() + ": Volviendo a la base.");
    }
}
