package EJ1_A1UD1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

public class EJ1_A1UD1
{
    public static void main(String[] args)
    {
        Optional<Integer> num = Optional.empty();

        System.out.println("Escribe el primer número:");

        int num1;

        while (num.isEmpty())
        {
            num = leerEntero();
        }

        num1 = num.get();

        System.out.println("Escribe el segundo número:");

        num = leerEntero();

        int num2;

        while (num.isEmpty())
        {
            num = leerEntero();
        }

        num2 = num.get();

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
        Optional<Integer> num = Optional.empty();
        try
        {
            String line = reader.readLine();
            num = Optional.of(Integer.parseInt(line));
        }
        catch (IOException | NumberFormatException e)
        {
            System.out.println("Error al leer el número. Inténtalo de nuevo.");
        }

        return num;
    }
}
