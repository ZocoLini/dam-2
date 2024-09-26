package EJ1_A3P2UD1;

import java.io.*;

public class EJ1_A3P2UD1
{
    private static final File OUTPUT = new File("Salida.txt");
    
    public static void main(String[] args)
    {
        for (var filePath : args)
        {
            try
            {
               escribirLineas(OUTPUT, visualizarLineas(filePath));
            }
            catch (Exception exception)
            {
               escribirError(OUTPUT, exception);
            }
        }
    }

    private static String visualizarLineas(String relPath) throws IOException
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(relPath)))
        {
            int contador = 0;
            
            while (reader.readLine() != null) 
            {
                contador++;
            }

            return "El archivo " + relPath + " tiene un total de " + contador + " lineas";
        }
    }
    
    private static void escribirLineas(File output, String line) throws IOException
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true)))
        {
            writer.write(line + "\n");
        }
    }
    
    private static void escribirError(File error, Exception e)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(error, true)))
        {
            writer.write(e.getMessage() + "\n");
        }
        catch (Exception exception)
        {
        }
    }
}
