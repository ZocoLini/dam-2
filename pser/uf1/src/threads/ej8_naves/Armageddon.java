package threads.ej8_naves;

public class Armageddon extends Nave
{
    public Armageddon(int identificador)
    {
        super(identificador);
    }

    @Override
    protected void act()
    {
        Meteorito meteorito;
        
        while ((meteorito = CentralHWWC.getInstance().obtenerNuevoMeteorito()) != null)
        {
            if (!meteorito.esTaladrabe()) continue;
            
            taladrar(meteorito);
        }
        
        System.out.println("Armageddon #" + getIdentificador() + ": No quedan meteoritos que taladrar, volviendo a la" +
                " base.");
    }
    
    private void taladrar(Meteorito meteorito)
    {
        try
        {
            synchronized (meteorito)
            {
                System.out.println("Armageddon #" + getIdentificador() + ": Taladrando meteorito #" + meteorito.getIdentificador());
                sleep(((int) (Math.random() * 1000)) + 1000);
                meteorito.taladrar();
                System.out.println("Armageddon #" + getIdentificador() + ": Meteorito #" + meteorito.getIdentificador() 
                        + " taladrado, esperando a que venga el bombardero.");
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
