package ej2;

public record Producto(int id, String nombre) 
{
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Producto producto)) return false;

        return id == producto.id;
    }

    @Override
    public int hashCode()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return "Producto #" + id + " (" + nombre + ")";
    }
}
