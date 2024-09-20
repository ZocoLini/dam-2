package EJ2_A1UD1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Teclado
{
    public static String leer()
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
            return reader.readLine();
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public static char leerChar()
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
            return (char) reader.read();
        }
        catch (Exception e)
        {
            return ' ';
        }
    }

    public static int leerInt()
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
        {
            return Integer.parseInt(reader.readLine());
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public static double leerDouble()
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)))
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
