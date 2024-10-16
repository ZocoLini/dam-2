package threads.ej3;

public class Partido
{
    private int votos = 0;
    private final String nombre;
    
    public Partido(String nombre)
    {
        this.nombre = nombre;
    }
    
    public synchronized void increment()
    {
        votos++;
    }

    public String getNombre()
    {
        return nombre;
    }

    public int getVotos()
    {
        return votos;
    }
}
