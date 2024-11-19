package threads.ej8_naves;

import java.util.ArrayList;
import java.util.List;

public class CentralHWWC
{
    private static CentralHWWC instance;
    
    public static CentralHWWC getInstance()
    {
        if (instance == null) instance = new CentralHWWC();
        
        return instance;
    }
    
    private CentralHWWC() {}

    private static final int NUM_METEORITOS = 2;
    private static final int NUM_NAVES_A = 2;
    private static final int NUM_NAVES_BS = 1;

    private final List<Meteorito> meteoritosVisibles = new ArrayList<>();
    
    public void init()
    {
        for (int i = 0; i < NUM_METEORITOS; i++)
        {
            final var e = new Meteorito(this::onDestruccionMeteorito);
            meteoritosVisibles.add(e);
        }
        
        for (int i = 0; i < NUM_NAVES_A; i++)
        {
            new Armageddon().start();
        }

        for (int i = 0; i < NUM_NAVES_BS; i++)
        {
            new BomberosSurtidores().start();
        }
    }
    
    public Meteorito obtenerNuevoMeteorito()
    {
        if (meteoritosVisibles.isEmpty()) return null;

        final var meteorito = meteoritosVisibles.get((int) (Math.random() * meteoritosVisibles.size()));
        
        if (!meteorito.esTaladrabe()) 
        {
            eliminarMeteoritoDeTalatrables(meteorito);
            return obtenerNuevoMeteorito();
        }
        
        return meteorito;
    }
    
    private final List<Meteorito> meteoritosTaladrados = new ArrayList<>();
    
    public synchronized void registrarMeteoritoComoTaladrado(Meteorito m)
    {
        meteoritosTaladrados.add(m);
        notify();
    }
    
    public synchronized Meteorito obtenerMeteoritoTaladrado()
    {
        if (meteoritosTaladrados.isEmpty())
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
        
        if (meteoritosTaladrados.isEmpty()) return null;
        
        return meteoritosTaladrados.removeFirst();
    }
    
    public synchronized void eliminarMeteoritoDeTalatrables(Meteorito m)
    {
        meteoritosVisibles.remove(m);
        
        if (meteoritosVisibles.isEmpty()) notifyAll();
    }
    
    public void aterrizar()
    {
        System.out.println("Nave aterrizando");
    }
    
    public void onDestruccionMeteorito(Meteorito m)
    {
        System.out.println("Meteorito destruido");
        eliminarMeteoritoDeTalatrables(m);
    }
}
