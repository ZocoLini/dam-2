package threads.ej5;

public class Main
{
    private static final int NUM_EMPLEADO = 5;
    public static void main(String[] args)
    {
        new Jefe().start();
        for (int i = 0; i < NUM_EMPLEADO; i++) 
        {
            new Empleado(String.valueOf(i)).start();
        }
    }
}
