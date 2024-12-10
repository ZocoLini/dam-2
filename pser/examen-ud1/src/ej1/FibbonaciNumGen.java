package ej1;

public class FibbonaciNumGen
{
    private int n0 = -1;
    private int n1 = -1;
    
    private int actualValue = -1;

    public int getActualValue()
    {
        if (actualValue == -1) throw new IllegalStateException();
        
        return actualValue;
    }

    public int nextValue()
    {
        int value = -1;
        
        if (n0 == -1) 
        {
            n0 = 1;
            value = n0;
        } else if (n1 == -1) 
        {
            n1 = 1;
            value = n1;
        } else
        {
            value = n0 + n1;

            n0 = n1;
            n1 = value;
        }
        
        actualValue = value;
        
        return value;
    }
}
