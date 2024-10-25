package threads.ej5;

public class Oficina
{
    private static Oficina instance;
    
    private boolean estaElJefe = false;
    
    public static Oficina getInstance()
    {
        if (instance == null) instance = new Oficina();
        
        return instance;
    }
    
    private Oficina() {}
    
    public synchronized void entrarComoEmpleado(Persona persona)
    {
        Empleado empleado = (Empleado) persona;
        
        if (estaElJefe) 
        {
            empleado.mensajeAlLlegarALaOficinaPostJefe();
        }
        else
        {
            empleado.mensajeAlLlegarALaOficinaPreJefe();
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
            empleado.mensajeAlLLegarElJefe();
        }
    }
    
    public synchronized void entrarComoJefe()
    {
        this.notifyAll();
        estaElJefe = true;
    }
}
