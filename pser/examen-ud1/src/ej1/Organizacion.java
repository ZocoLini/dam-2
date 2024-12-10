package ej1;



public abstract class Organizacion extends Thread
{
    private static final int APOTACION_MONETARIA_AL_FONDO = 10;
    private static final int MS_ENTRE_INGRESOS = 1000;

    private boolean continuarIngresando = true;
    
    protected abstract String getNombre();
    
    public void detenerIngresos()
    {
        continuarIngresando = false;
    }
    
    @Override
    public final  void run()
    {
        while (continuarIngresando)
        {
            System.out.println(getNombre() + ": Ingresando " + APOTACION_MONETARIA_AL_FONDO + "€ en el dia " + DayTimer.getInstance().currentDay());
            
            FondoCentral.getInstance().ingresarDinero(APOTACION_MONETARIA_AL_FONDO);
            
            try
            {
                sleep(MS_ENTRE_INGRESOS);
            }
            catch (InterruptedException e) {}
        }
    }
}
