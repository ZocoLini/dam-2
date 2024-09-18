package EJ2_A1UD1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Teclado
{
    public static String leer() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try
        {
            return reader.readLine();
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public static char leerChar() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try
        {
            return (char) reader.read();
        }
        catch (Exception e)
        {
            return ' ';
        }
    }

    public static int leerInt() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try
        {
            return Integer.parseInt(reader.readLine());
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public static double leerDouble() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        try
        {
            return Double.parseDouble(reader.readLine());
        }
        catch (Exception e)
        {
            return 0.0;
        }
    }
    
    public static int leerPositivo()
    {
        int num = leerInt();
        
        if (num <= 0) 
        {
            throw new NotPositiveIntegerException();
        }
        
        return num;
    }
}
