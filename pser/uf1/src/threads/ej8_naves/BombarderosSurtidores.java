package threads.ej8_naves;

import java.util.function.Consumer;

public class BombarderosSurtidores extends Nave
{
    private final Consumer<Meteorito> onBombaColocada;
    
    public BombarderosSurtidores(int identificador, Consumer<Meteorito> onBombaColocada)
    {
        super(identificador);
        this.onBombaColocada = onBombaColocada;
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
                onBombaColocada.accept(meteorito);
                meteorito.notifyAll();
            }
        }

        System.out.println("Bombardero #" + getIdentificador() + ": Volviendo a la base.");
    }
}
