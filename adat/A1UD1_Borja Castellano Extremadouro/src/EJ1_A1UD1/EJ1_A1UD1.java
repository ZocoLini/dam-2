package EJ1_A1UD1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class EJ1_A1UD1
{
    public static void main(String[] args)
    {
        System.out.println("Escribe el primer número:");
        
        Optional<Integer> num = leerEntero();
        
        int num1;
        
        if (num.isPresent()) 
        {
            num1 = num.get();
        }
        else
        {
            System.err.println("Error al leer el primer número.");
            return;
        }
        
        System.out.println("Escribe el segundo número:");
        
        num = leerEntero();
        
        int num2;
        
        if (num.isPresent()) 
        {
            num2 = num.get();
        }
        else
        {
            System.err.println("Error al leer el segundo número.");
            return;
        }
        
        System.out.println("La suma es: " + (num1 + num2));
        System.out.println("La resta es: " + (num1 - num2));
        System.out.println("La multiplicación es: " + (num1 * num2));
        System.out.println("La división entera es: " + (num1 / num2));
        System.out.println("La división real es: " + (double) num1 / num2);
        System.out.println("El resto de la división es: " + (num1 % num2));
    }
    
    private static Optional<Integer> leerEntero()
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            return Optional.of(Integer.valueOf(String.valueOf((char) reader.read())));
        }
        catch (IOException | NumberFormatException e)
        {
            System.out.println("Error al leer el número. Inténtalo de nuevo.");
            
            return leerEntero();
        }
    }
}
