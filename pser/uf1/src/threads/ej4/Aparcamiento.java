package threads.ej4;

public class Aparcamiento
{
    private static final int NUM_PLAZAS = 10;
    
    private static Aparcamiento instance;
    
    public static Aparcamiento getInstance()
    {
        if (instance == null) instance = new Aparcamiento();
        
        return instance;
    }
    
    private final PlazaAparcamiento[] plazasAparcamiento = new PlazaAparcamiento[NUM_PLAZAS];
    
    private Aparcamiento()
    {
        for (int i = 0; i < plazasAparcamiento.length; i++) 
        {
            plazasAparcamiento[i] = new PlazaAparcamiento();
        }
    }
    
    private void imprimirEstado(String mensaje)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("INFORMACION").append("\n");
        sb.append(mensaje).append("\n");
        
        for (int i = 0; i < plazasAparcamiento.length; i++)
        {
            if (i == (int) (plazasAparcamiento.length / 2.0)) 
            {
                sb.append("\n");
            }
            
            sb.append((plazasAparcamiento[i].toString()));
        }

        sb.append("\n");
        System.out.println(sb);
    }
    
    public synchronized void salir(Conductor conductor)
    {
        for (PlazaAparcamiento plazaAparcamiento : plazasAparcamiento)
        {
            if (plazaAparcamiento.ocupada() && plazaAparcamiento.conductor.equals(conductor))
            {
                plazaAparcamiento.vaciar();
                imprimirEstado("SALIDA");
                break;
            }
        }
        
       notify();
    }
    
    public synchronized void aparcar(Conductor conductor) throws InterruptedException
    {
        while (true)
        {
            for (int i = 0; i < NUM_PLAZAS; i++)
            {
                final var plazaAparcamiento = plazasAparcamiento[i];
                
                if (plazaAparcamiento.ocupada()) continue;

                plazaAparcamiento.ocupar(conductor);
                imprimirEstado("ENTRADA");
                return;
            }
            
            wait();
        }
    }
}
