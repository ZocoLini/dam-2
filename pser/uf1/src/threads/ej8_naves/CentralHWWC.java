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

    private static final int NUM_METEORITOS = 11;
    private static final int NUM_NAVES_A = 10;
    private static final int NUM_NAVES_BS = 20;

    private final Set<Meteorito> meteoritosSinBomba = new HashSet<>();
    
    private final TrackerMeteoritosTaladrables trackerMeteoritosTaladrables = new TrackerMeteoritosTaladrables();
    private final TrackerDeMeteoritosTaladrados trackerDeMeteoritosTaladrados = new TrackerDeMeteoritosTaladrados();
    
    public void init()
    {
        for (int i = 0; i < NUM_METEORITOS; i++)
        {
            final var meteorito = new Meteorito(i);
            meteoritosSinBomba.add(meteorito);
            trackerMeteoritosTaladrables.registrarMeteoritoTaladrable(meteorito);
        }
        
        for (int i = 0; i < NUM_NAVES_A; i++)
        {
            new Armageddon(i).start();
        }

        for (int i = 0; i < NUM_NAVES_BS; i++)
        {
            final var bombarderosSurtidores = new BombarderosSurtidores(i, meteorito ->
            {
                meteoritosSinBomba.remove(meteorito);

                if (meteoritosSinBomba.isEmpty()) trackerDeMeteoritosTaladrados.notificarVueltaABase();
            });
            
            bombarderosSurtidores.start();
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
            if (CentralHWWC.instance.meteoritosSinBomba.isEmpty()) return null;
            
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
            
            // Puede suceder que un bombardero este esperando un meteorito pero ya otras naves se ocupaon de todos 
            // antes de su turno asi que devolvemos que no hay nada despues de la epsera
            if (meteoritosTaladrados.isEmpty()) return null; 
            
            return meteoritosTaladrados.pop();
        }
        
        public synchronized void notificarVueltaABase()
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
