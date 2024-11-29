package ej1;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    private static final int NUM_POLITICOS = 2;

    private static final List<Organizacion> organizaciones = new ArrayList<>(List.of(
            new Caritas(),
            new CruzRoja()
    ));
    
    public static void main(String[] args)
    {
        FondoCentral.getInstance();
        DayTimer.getInstance().initialize();

        organizaciones.forEach(Thread::start);

        List<Politico> politicos = new ArrayList<>();
        
        for (int i = 0; i < NUM_POLITICOS; i++) 
        {
            final var politico = new Politico(i);
            politicos.add(politico);
            politico.start();
        }
        
        try
        {
            for (var politico : politicos)
            {
                politico.join();
            }
        }
        catch (InterruptedException e) {}

        organizaciones.forEach(Organizacion::detenerIngresos);
    }
}
