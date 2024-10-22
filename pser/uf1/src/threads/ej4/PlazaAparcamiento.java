package threads.ej4;

public class PlazaAparcamiento
{
    Conductor conductor;
    
    public boolean ocupada()
    {
        return conductor != null;
    }
    
    public void ocupar(Conductor conductor)
    {
        this.conductor = conductor;
    }
    
    public void vaciar()
    {
        conductor = null;
    }

    @Override
    public String toString()
    {
        return conductor == null ? "X " : conductor.getNumero() + " ";
    }
}
