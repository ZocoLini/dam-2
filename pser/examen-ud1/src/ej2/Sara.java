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

    private Sara()
    {
        int i = 0;
        for (var nombre : nombresProductos)
        {
            productosDisponibles.put(new Producto(++i, nombre), CANTIDAD_STOCK_POR_PRODUCTO);
        }
    }

    public synchronized Producto cogerProductoAleatorio()
    {
        List<Producto> productosDisponibles = this.productosDisponibles.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(Map.Entry::getKey)
                .toList();

        if (!productosDisponibles.isEmpty())
        {
            int psicionProductoAleatorio = (int) (productosDisponibles.size() * Math.random());

            return productosDisponibles.get(psicionProductoAleatorio);
        }

        return null;
    }

    public synchronized boolean comprarProducto(Producto producto, Cliente cliente)
    {
        if (productosDisponibles.get(producto) == 0) 
        {
            return false;
        }
        
        productosDisponibles.put(producto, productosDisponibles.get(producto) - 1);

        System.out.printf("%s - %s - %d\n", 
                producto.toString(), 
                cliente.getClienteName(), 
                productosDisponibles.get(producto)
        );
        
        return true;
    }
    
    public boolean quedaMenosOIgualQueLaMitadDelStock(Producto producto)
    {
        return productosDisponibles.get(producto) <= CANTIDAD_STOCK_POR_PRODUCTO / 2.0;
    }
}
