package ej1;

public class DayTimer
{
    public static final int DURACION_UN_DIA_MS = 1000;
    
    private static DayTimer instance;
    
    public static DayTimer getInstance()
    {
        if (instance == null) instance = new DayTimer();
        
        return instance;
    }
    
    private long momentoInicio;
    
    private DayTimer() {}
    
    public void initialize()
    {
        momentoInicio = System.currentTimeMillis();
    }
    
    public int currentDay()
    {
        return (int) ((System.currentTimeMillis() - momentoInicio) / DURACION_UN_DIA_MS);
    }
}
