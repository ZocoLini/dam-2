package ej2;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sara
{
    private static final int CANTIDAD_STOCK_POR_PRODUCTO = 2;

    private static final String[] nombresProductos = {
            "Blusa", "Calzones", "Tanga", "Body", "Vaqueros", "Traje", "Calcetines",
            "Gorra", "Bufanda", "Camisa"
    };

    private static Sara instance;

    public static Sara getInstance()
    {
        if (instance == null) instance = new Sara();

        return instance;
    }

    private final Map<Producto, Integer> productosDisponibles = new HashMap<>();
    private final Map<Producto, Integer> productosAunNoComprados = new HashMap<>();

    private boolean finalDeExistencias = false;

    private Sara()
    {
        int i = 0;
        for (var nombre : nombresProductos)
        {
            final var producto = new Producto(++i, nombre);
            productosDisponibles.put(producto, CANTIDAD_STOCK_POR_PRODUCTO);
            productosAunNoComprados.put(producto, CANTIDAD_STOCK_POR_PRODUCTO);
        }
    }

    public synchronized Producto cogerProductoAleatorio()
    {
        if (finalDeExistencias)
        {
            return null;
        }
        
        List<Producto> productosDisponibles = this.productosDisponibles.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .toList();

        if (!productosDisponibles.isEmpty())
        {
            int psicionProductoAleatorio = (int) (productosDisponibles.size() * Math.random());
            
            final var producto = productosDisponibles.get(psicionProductoAleatorio);

            this.productosDisponibles.put(producto, this.productosDisponibles.get(producto) - 1);

            
            
            return producto;
        }
        

        try
        {
            wait();
        }
        catch (InterruptedException e) {}


        return cogerProductoAleatorio();
    }

    public synchronized void decidirNoComprar(Producto producto)
    {
        productosDisponibles.put(producto, productosDisponibles.get(producto) + 1);
        notifyAll();
    }

    public synchronized void comprarProducto(Producto producto, Cliente cliente)
    {
        productosAunNoComprados.put(producto, productosAunNoComprados.get(producto) - 1);

        System.out.printf("%s - %s - %d\n", producto.toString(), cliente.getClienteName(),
                productosAunNoComprados.get(producto));

        if (todosLosProductosVendidos())
        {
            finalDeExistencias = true;
            notifyAll();
        }
    }
    
    public boolean quedaMenosOIgualQueLaMitadDelStock(Producto producto)
    {
        return productosAunNoComprados.get(producto) <= CANTIDAD_STOCK_POR_PRODUCTO / 2.0;
    }

    private boolean todosLosProductosVendidos()
    {
        return productosAunNoComprados.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .toList().isEmpty();
    }
}
