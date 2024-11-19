package threads.ej8_naves;

public class Armageddon extends Nave
{
    @Override
    protected void act()
    {
        Meteorito meteorito;
        
        while ((meteorito = CentralHWWC.getInstance().obtenerNuevoMeteorito()) != null)
        {
            if (!meteorito.esTaladrabe()) continue;
            
            taladrar(meteorito);
        }
    }
    
    private void taladrar(Meteorito meteorito)
    {
        try
        {
            synchronized (meteorito)
            {
                System.out.println("Taladrando meteorito...");
                sleep(((int) (Math.random() * 1000)) + 1000);
                meteorito.taladrar();
                System.out.println("Meteorito taladrado, esperando a que venga el bombardero.");
                CentralHWWC.getInstance().registrarMeteoritoComoTaladrado(meteorito);
                meteorito.wait();
            }
            
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
