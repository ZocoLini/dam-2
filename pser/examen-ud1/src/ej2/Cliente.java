package ej2;


import rand.RandomResult;

import java.util.HashSet;
import java.util.Set;

public class Cliente extends Thread
{
    private static final int MIN_MS_PENSAMIENTO = 400;
    private static final int MAX_MS_PENSAMIENTO = 500;
    private static final float PORCENTAJE_COMPRA = 0.3f;

    private final int identificador;
    
    public Cliente(int identificador)
    {
        this.identificador = identificador;
    }
    
    private final Set<Producto> productosComprados = new HashSet<>();

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
            if (productosComprados.size() == Sara.getInstance().getCantidadProductosALaVenta()) break;
            
            if (productosComprados.contains(producto))
            {
                Sara.getInstance().decidirNoComprar(producto);
                continue;
            }
            
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
                else
                {
                    Sara.getInstance().decidirNoComprar(producto);
                }
            }
        }

        System.out.println(getClienteName() + " ha terminado de comprar");
    }

    private void realizarCompraEnSara(Producto producto)
    {
        Sara.getInstance().comprarProducto(producto, this);
        productosComprados.add(producto);
    }
}
