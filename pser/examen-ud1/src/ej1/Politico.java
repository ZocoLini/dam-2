package ej1;


import rand.RandomResult;

public class Politico extends Thread
{
    private static final int MIN_MS_ESPERA = 500;
    private static final int MAX_MS_ESPERA = 1000;
    
    public static final int NUM_RETIRADAS = 2;
    
    private final FibbonaciNumGen fibbonaci = new FibbonaciNumGen();
    private final int identifier;
    
    public Politico(int identifier)
    {
        this.identifier = identifier;
        fibbonaci.nextValue();
    }

    public String getPolitName()
    {
        return "Político #" + identifier;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < NUM_RETIRADAS;) 
        {
            if (FondoCentral.getInstance().retirarDinero(fibbonaci.getActualValue())) 
            {
                System.out.println(getPolitName() + " ha retirado " + fibbonaci.getActualValue() + "€ en el dia " + DayTimer.getInstance().currentDay());
                
                fibbonaci.nextValue();
                i++;

                
                try
                {
                    sleep(RandomResult.getInt(MIN_MS_ESPERA, MAX_MS_ESPERA));
                }
                catch (InterruptedException e) {}
            }
        }

        System.out.println(getPolitName() + " terminado.");
    }
}
