package threads.ej4;

import java.util.ArrayDeque;
import java.util.Queue;

public class Aparcamiento
{
    private static final int NUM_PLAZAS = 10;
    
    private static Aparcamiento instance;
    
    public static Aparcamiento getInstance()
    {
        if (instance == null) instance = new Aparcamiento();
        
        return instance;
    }
    
    private final Conductor[] plazasConductores = new Conductor[NUM_PLAZAS];
    private int cursorActual = -1;
    private final Queue<Conductor> colaConductores = new ArrayDeque<>();
    
    private void imprimirEstado()
    {
        System.out.println("INFORMACION");
        for (int i = 0; i < plazasConductores.length; i++)
        {
            if (i == (int) (plazasConductores.length / 2.0)) 
            {
                System.out.println();
            }
            
            System.out.print(plazasConductores[i] == null ? "X " : plazasConductores[i].getNumero() + " ");
        }
        
        System.out.println();
        System.out.println();
    }
    
    public void salir(Conductor conductor)
    {
        for (int i = 0; i < plazasConductores.length; i++)
        {
            if (plazasConductores[i] == conductor)
            {
                plazasConductores[i] = null;
                break;
            }
        }

        Conductor siguiente = colaConductores.poll();

        if (siguiente != null)
        {
            synchronized (siguiente)
            {
                siguiente.notify();
            }
        }
    }
    
    public boolean intentarAparcar(Conductor conductor)
    {
        for (int i = 0; i < NUM_PLAZAS; i++) 
        {
            if (plazasConductores[incrementarCursor()] == null)
            {
                plazasConductores[cursorActual] = conductor;
                imprimirEstado();
                return true;
            }
        }
        
        colaConductores.add(conductor);
        return false;
    }
    
    private int incrementarCursor()
    {
        cursorActual++;
        cursorActual %= plazasConductores.length;
        
        return cursorActual;
    }
}
