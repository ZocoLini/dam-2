package ej2;


import rand.RandomResult;

import java.util.HashSet;
import java.util.Set;

public class Cliente extends Thread
{
    private static final int MIN_MS_PENSAMIENTO = 10;
    private static final int MAX_MS_PENSAMIENTO = 500;
    private static final float PORCENTAJE_COMPRA = 0.3f;

    private final int identificador;
    
    public Cliente(int identificador)
    {
        this.identificador = identificador;
    }
    
    private static Set<Producto> productosComprados = new HashSet<>();

    public String getClienteName()
    {
        return "Cliente #" + identificador;
    }
    
    @Override
    public void run()
    {
        Producto producto;

        while ((producto = Sara.getInstance().cogerProductoAleatorio()) != null)
        {
            System.out.println("Pillado " + getClienteName() + " " + producto);
            
            if (productosComprados.contains(producto)) continue;

            System.out.println(getClienteName() + " " + producto);
            
            if (Sara.getInstance().quedaMenosOIgualQueLaMitadDelStock(producto))
            {
                realizarCompraEnSara(producto);
            }
            else
            {
                try
                {
                    sleep(RandomResult.getInt(MIN_MS_PENSAMIENTO, MAX_MS_PENSAMIENTO));
                }
                catch (InterruptedException e) {}

                if (RandomResult.getBoolean(PORCENTAJE_COMPRA))
                {
                    realizarCompraEnSara(producto);
                }
            }
        }

        System.out.println(getClienteName() + " ha terminado de comprar");
    }

    private void realizarCompraEnSara(Producto producto)
    {
        if (!Sara.getInstance().comprarProducto(producto, this)) return;
        
        productosComprados.add(producto);
    }
}
