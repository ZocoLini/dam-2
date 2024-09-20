package EJ3_A1UD1;

import EJ2_A1UD1.NotPositiveIntegerException;
import EJ2_A1UD1.Teclado;

import java.io.*;
import java.util.Scanner;

public class EJ3_A1UD1
{
    private static final File f = new File("NumerosPositivos.txt");
    private static PrintStream ps;

    public static void main(String[] args) throws IOException, NotPositiveIntegerException
    {
        abrirFichero();
        System.out.println("Introduce el número de enteros positivos para grabar en fichero:");
        int num = leerPositivo();
        for (int i = 1; i <= num; i++)
        {
            System.out.print("número " + i + ":");
            grabarFichero(leerPositivo());
        }
        cerrarFichero();
        LeerFichero();
    }

    private static void abrirFichero()
    {
        try
        {
            ps = new PrintStream(new FileOutputStream(f));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error al abrir el fichero");
        }
    }

    private static int leerPositivo() throws NotPositiveIntegerException
    {
        return Teclado.leerPositivo();
    }

    private static void grabarFichero(int num)
    {
        ps.print(num + ";");
    }

    private static void cerrarFichero()
    {
        ps.close();
        ps = null;
    }

    private static void LeerFichero() throws FileNotFoundException
    {
        try (Scanner scanner = new Scanner(new FileInputStream(f)))
        {
            scanner.useDelimiter(";");

            while (scanner.hasNext())
            {
                System.out.println(scanner.next());
            }
        }
    }
}





















