package threads.ej9_venta_coches;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Concesionario
{
    private static Concesionario instance;
    
    public static Concesionario getInstance()
    {
        if (instance == null) instance = new Concesionario();
        
        return instance;
    }
    
    private static final int UNIDADES_LEON = 10;
    private static final int UNIDADES_IBIZA = 7;
    private static final int UNIDADES_ATECA = 3;
    
    private static final Stack<Seat> seatsDisponibles = new Stack<>();
    private static final Set<Seat> seatsVendibles = new HashSet<>();
    private static final HashMap<Cliente, Seat> compradores = new HashMap<>();
    
    private Concesionario() 
    {
        new Thread(() ->
        {
            for (int i = 0; i < UNIDADES_LEON; i++)
            {
                synchronized (seatsVendibles)
                {
                    seatsVendibles.add(new SeatLeon(i));
                }
            }
        }).start();
        
        new Thread(() ->
        {
            for (int i = 0; i < UNIDADES_IBIZA; i++)
            {
                synchronized (seatsVendibles)
                {
                    seatsVendibles.add(new SeatIbiza(i));
                }
            }
        }).start();
        
        new Thread(() ->
        {
            for (int i = 0; i < UNIDADES_ATECA; i++)
            {
                synchronized (seatsVendibles)
                {
                    seatsVendibles.add(new SeatAteca(i));
                }
            }
        }).start();

        seatsDisponibles.addAll(seatsVendibles);
    }
    
    public synchronized Seat pedirCitaParaVerUnCoche()
    {
        if (seatsDisponibles.isEmpty())
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
        
        if (seatsDisponibles.isEmpty()) return null;
        
        return seatsDisponibles.pop();
    }
    
    public synchronized void dejarDeVerUnCocheSinComprar(Seat seat)
    {
        seatsDisponibles.push(seat);
        seat.aumentarVecesVisto();
        
        notify();
    }
    
    public synchronized void dejarDeVerUnCocheComprando(Seat seat, Cliente cliente)
    {
        compradores.put(cliente, seat);
        seatsVendibles.remove(seat);
        
        if (seatsVendibles.isEmpty()) 
        {
            notifyAll();
        }
    }
    
    public void realizarRecuentoDeCompras()
    {
        AtomicInteger suma = new AtomicInteger();
        
        compradores.forEach((cliente, coche) ->
        {
            System.out.printf("El cliente %s ha comprado el coche %s después de haber sido visto %d veces\n",
                    cliente.getIdentificador(),
                    coche.getIdCoche(),
                    coche.getContadorVecesVisto()
            );
            
            suma.addAndGet(coche.getContadorVecesVisto());
        });

        System.out.println((float) suma.get() / compradores.size());
    }
}
