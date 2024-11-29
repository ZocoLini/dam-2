package ej2;

public class Main
{
    private static final int NUM_CLIENTES = 1;
    
    public static void main(String[] args)
    {
        Sara.getInstance();
        
        for (int i = 0; i < NUM_CLIENTES; i++) 
        {
            new Cliente(i).start();
        }
    }
}
