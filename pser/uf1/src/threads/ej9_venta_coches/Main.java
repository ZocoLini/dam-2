package threads.ej9_venta_coches;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    private static final int NUM_CLIENTES = 5;
    
    public static void main(String[] args)
    {
        Concesionario.getInstance();

        List<Cliente> clientes = new ArrayList<>(NUM_CLIENTES);
        
        for (int i = 0; i < NUM_CLIENTES; i++) 
        {
            final var cliente = new Cliente(i);
            clientes.add(cliente);
            cliente.start();
        }
        
        clientes.forEach(cliente ->
        {
            try { cliente.join(); }catch (InterruptedException _) {}
        });
        
        Concesionario.getInstance().realizarRecuentoDeCompras();
    }
}
