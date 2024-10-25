package threads.ej5;

public class Jefe extends Persona
{
    public Jefe()
    {
        super("El jefe");
    }

    @Override
    protected void empezarJornada()
    {
        System.out.println("EL JEFE HA LLEGADO");
        Oficina.getInstance().entrarComoJefe();
    }
}
