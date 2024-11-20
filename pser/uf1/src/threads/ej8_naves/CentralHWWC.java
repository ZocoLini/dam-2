package threads.ej8_naves;

import java.util.*;

public class CentralHWWC
{
    private static CentralHWWC instance;
    
    public static CentralHWWC getInstance()
    {
        if (instance == null) instance = new CentralHWWC();
        
        return instance;
    }
    
    private CentralHWWC() {}

    private static final int NUM_METEORITOS = 4;
    private static final int NUM_NAVES_A = 1;
    private static final int NUM_NAVES_BS = 2;

    private final TrackerMeteoritosTaladrables trackerMeteoritosTaladrables = new TrackerMeteoritosTaladrables();
    private final TrackerDeMeteoritosTaladrados trackerDeMeteoritosTaladrados = new TrackerDeMeteoritosTaladrados();
    
    public void init()
    {
        for (int i = 0; i < NUM_METEORITOS; i++)
        {
            trackerMeteoritosTaladrables.registrarMeteoritoTaladrable(new Meteorito(i));
        }
        
        for (int i = 0; i < NUM_NAVES_A; i++)
        {
            new Armageddon(i).start();
        }

        for (int i = 0; i < NUM_NAVES_BS; i++)
        {
            new BombarderosSurtidores(i).start();
        }
    }
    
    public Meteorito obtenerNuevoMeteorito()
    {
        return trackerMeteoritosTaladrables.obtenerMeteoritoTaladrable();
    }
    
    public void registrarMeteoritoComoTaladrado(Meteorito m)
    {
        trackerDeMeteoritosTaladrados.registrarMeteoritoTaladrado(m);
    }
    
    public Meteorito obtenerMeteoritoTaladrado()
    {
        return trackerDeMeteoritosTaladrados.esperarMeteoritoTaladrado();
    }

    private boolean estaTrackeandoAlgunMeterito()
    {
        return !trackerMeteoritosTaladrables.meteoritosTaladrables.isEmpty();
    }

    public void aterrizar()
    {
        System.out.println("Nave aterrizando");
    }
    
    private static class TrackerDeMeteoritosTaladrados
    {

        private final Stack<Meteorito> meteoritosTaladrados = new Stack<>();

        public synchronized void registrarMeteoritoTaladrado(Meteorito m)
        {
            meteoritosTaladrados.add(m);
            notify();
        }

        public synchronized Meteorito esperarMeteoritoTaladrado()
        {
            if (meteoritosTaladrados.isEmpty())
            {
                if (!CentralHWWC.getInstance().estaTrackeandoAlgunMeterito()) 
                {
                    notificarVueltaABase();
                    return null;
                }
                
                try
                {
                    wait();
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }
            
            // Puede suceder que un bombardero este esperando un meteorito pero ya otras naves se ocupaon de todos 
            // antes de su turno asi que devolvemos que no hay nada despues de la epsera
            if (meteoritosTaladrados.isEmpty()) return null; 
            
            return meteoritosTaladrados.pop();
        }
        private void notificarVueltaABase()
        {
            System.out.println("CentralHWWC: Notificando vuelta a base a todas las naves bombardero");
            notifyAll();
        }

    }

    private static class TrackerMeteoritosTaladrables
    {
        private final List<Meteorito> meteoritosTaladrables = new ArrayList<>();
        
        public void registrarMeteoritoTaladrable(Meteorito m)
        {
            meteoritosTaladrables.add(m);
        }
        
        public synchronized Meteorito obtenerMeteoritoTaladrable()
        {
            if (meteoritosTaladrables.isEmpty()) return null;
            
            return meteoritosTaladrables.remove((int) (Math.random() * meteoritosTaladrables.size()));
        }
    }
}
