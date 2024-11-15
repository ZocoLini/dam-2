package threads.ej7_apuestas;

import java.util.ArrayList;

public class Main
{
    private final static int NUM_EMPLEADOS = 20;
    
    public static void main(String[] args)
    {
        Porra.getInstance();

        ArrayList<Apostador> apostadores = new ArrayList<>();
        
        for (int i = 0; i < NUM_EMPLEADOS; i++)
        {
            final var apostador = new Apostador();
            apostadores.add(apostador);
            apostador.start();
        }
        
        apostadores.forEach(apostador -> 
        {
            try {apostador.join();}catch (InterruptedException e) {throw new RuntimeException(e);}
        });
        
        Porra.getInstance().finalizarPeriodoApuestas();
    }
}
