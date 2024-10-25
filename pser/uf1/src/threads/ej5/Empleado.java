package threads.ej5;

public class Empleado extends Persona
{
    public Empleado(String nombre)
    {
        super(nombre);
    }

    @Override
    protected void empezarJornada()
    {
        Oficina.getInstance().entrarComoEmpleado(this);
    }
    
    public void mensajeAlLlegarALaOficinaPreJefe()
    {
        System.out.println(nombre + " ha llegado. ZZZZZZ");
    }
    
    public void mensajeAlLlegarALaOficinaPostJefe()
    {
        System.out.println(nombre + " ha llegado. Hola jefe!, me pongo a trabajar...");
    }
    
    public void mensajeAlLLegarElJefe()
    {
        System.out.println(nombre + " desperezandose. Buenos días jefe, aquí estoy trabajando");
    }
}
