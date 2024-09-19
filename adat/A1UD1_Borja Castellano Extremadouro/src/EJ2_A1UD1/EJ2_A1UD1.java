package EJ2_A1UD1;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class EJ2_A1UD1
{
    public static void main(String[] args)
    {
        File f = new File("Datos.txt");

        print("Leer cadena: ");
        String s = Teclado.leer();
        print("Leer caracter: ");
        char car = Teclado.leerChar();
        print("Leer numero entero: ");
        int num1 = Teclado.leerInt();
        print("Leer numero real double: ");
        double num2 = Teclado.leerDouble();
        print("Leer entero positivo: ");
        int numPos = leerPositivo();
        print(" cadena: " + s + "\n"
                        + " caracter: " + car + "\n"
                        + " numero entero: " + num1 + "\n"
                        + " numero real double: " + num2 + "\n"
                        + " entero positivo: " + numPos + "\n",
                f);
    }

    private static int leerPositivo()
    {
        try
        {
            return Teclado.leerPositivo();
        }
        catch (NotPositiveIntegerException e)
        {
            print("El número introducido no es positivo. Inténtelo de nuevo: ");
            return leerPositivo();
        }
    }

    private static void print(String s)
    {
        BufferedOutputStream bs = new BufferedOutputStream(System.out);

        try
        {
            bs.write(s.getBytes());
            bs.flush();
        }
        catch (Exception e)
        {
            System.err.println("Error al escribir en la salida estándar.");
        }
    }

    private static void print(String s, File f)
    {
        try
        {
            if (!f.exists()) f.createNewFile();

            BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(f));
            bs.write(s.getBytes());
            bs.flush();
        }
        catch (Exception e)
        {
            System.err.println("Error al escribir en la salida estándar.");
        }
    }
}
