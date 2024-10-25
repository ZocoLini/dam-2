package threads.ej4;

import java.util.ArrayList;

public class Main
{
    private static final int CONDUCTORES = 50;
    
    public static void main(String[] args)
    {
        ArrayList<Conductor> conductores = new ArrayList<>();
        
        for (int i = 0; i < CONDUCTORES; i++) 
        {
            Conductor conductor = new Conductor(i);
            conductores.add(conductor);
            conductor.start();
        }
        
        try
        {
            for (Conductor conductor : conductores)
            {
                conductor.join();
            }
        }
        catch (Exception exception) {}
    }
}
