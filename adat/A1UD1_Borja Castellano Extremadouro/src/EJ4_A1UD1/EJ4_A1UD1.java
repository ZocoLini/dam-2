package EJ4_A1UD1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EJ4_A1UD1
{
    private static final File f = new File("NumerosPositivos.txt");

    public static void main(String[] args)
    {
        System.out.println("Leyendo los numeros del fichero NumerosPositivos.txt");

        List<Integer> numeros = leerFichero();

        System.out.println("Ordenando los números");

        numeros.sort(null);

        System.out.println("Escribiendo los números ordenados en el fichero NumerosPositivos.txt");

        escribirFichero(numeros);
    }

    private static List<Integer> leerFichero()
    {
        List<Integer> numeros = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(f)))
        {
            scanner.useDelimiter(";");
            while (scanner.hasNextInt())
            {
                numeros.add(scanner.nextInt());
            }
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception);
        }

        return numeros;
    }

    private static void escribirFichero(List<Integer> numeros)
    {
        try (PrintStream ps = new PrintStream(new FileOutputStream("NumerosPositivos.txt")))
        {
            for (int num : numeros)
            {
                ps.print(num + ";");
            }
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception);
        }
    }
}
