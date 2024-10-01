package threads.ej2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main
{
    private static final int NUM_HILOS = 10;
    private static final int NUM_INCREMENTOS = 10;
    private static final AtomicInteger numero = new AtomicInteger(0);
    
    public static void main(String[] args)
    {
        List<Thread> threads = new ArrayList<>(NUM_HILOS);
        
        for (int i = 0; i < NUM_HILOS; i++) 
        {
            var hilo = new Thread(() -> {
                for (int j = 0; j < NUM_INCREMENTOS; j++) 
                {
                    numero.addAndGet(1);
                }
            });
            
            hilo.start();
            threads.add(hilo);
        }
        
        try
        {
            for (var variable : threads)
            {
                variable.join();
            }
        }
        catch (Exception ignore) {}

        System.out.println(numero.get());
        assert numero.get() == NUM_HILOS * NUM_INCREMENTOS;
    }
}
