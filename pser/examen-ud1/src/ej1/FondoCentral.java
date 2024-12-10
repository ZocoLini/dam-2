package ej1;

public class FondoCentral
{
    private static FondoCentral instance;

    public static FondoCentral getInstance()
    {
        if (instance == null) instance = new FondoCentral();

        return instance;
    }

    private int dineroDisponible;

    private FondoCentral() {}

    public synchronized void ingresarDinero(int cantidad)
    {
        System.out.println("Se ha recibido una solicitud de ingreso por valor de "
                + cantidad + "€ en el dia " + DayTimer.getInstance().currentDay());
        realizarOperacionEnElFondo(cantidad);
        notifyAll();
    }

    public synchronized boolean retirarDinero(int cantidad)
    {
        if (dineroDisponible >= cantidad)
        {
            System.out.println("Se ha recibido una solicitud de retirada por valor de " 
                    + cantidad + "€ en el dia " + DayTimer.getInstance().currentDay());
            realizarOperacionEnElFondo(-cantidad);
            return true;
        }
        else
        {
            try
            {
                wait();
            }
            catch (Exception exception) {}
            return false;
        }
    }
    
    private void realizarOperacionEnElFondo(int operacion)
    {
        dineroDisponible += operacion;
        System.out.println("Cantidad disponible en el fondo: " + dineroDisponible + "€.");
    }
}
