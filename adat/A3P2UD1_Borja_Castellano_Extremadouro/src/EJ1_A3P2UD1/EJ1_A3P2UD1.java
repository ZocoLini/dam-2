package EJ1_A3P2UD1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EJ1_A3P2UD1
{
    public static void main(String[] args)
    {
        for (var filePath : args)
        {
            visualizarLineas(filePath);
        }
    }

    private static void visualizarLineas(String relPath)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(relPath)))
        {
            int contador = 0;
            
            while (reader.readLine() != null) 
            {
                contador++;
            }

            System.out.println("El archivo " + relPath + " tiene un total de " + contador + " lineas");
        }
        catch (FileNotFoundException e)
        {
            System.out.println("El archivo " + relPath + " no existe.");
        }
        catch (IOException e)
        {
            System.out.println("Error de IO");
        }
    }
}
