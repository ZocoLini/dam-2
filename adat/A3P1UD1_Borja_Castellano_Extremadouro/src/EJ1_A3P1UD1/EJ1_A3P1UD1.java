package EJ1_A3P1UD1;

import java.io.*;

public class EJ1_A3P1UD1
{
    public static void main(String[] args)
    {
        File file = new File("data.txt");
        
        grabarNumeros(file);
        visializarContenido(file);
    }
    
    private static void grabarNumeros(File file)
    {
        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(file)))
        {
            writer.write(generarNumeroAleatorios());
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No se ha encontrado el fichero");
        }
        catch (IOException e)
        {
            System.out.println("Error de entrada salida");
        }
    }
    
    private static byte[] generarNumeroAleatorios()
    {
        byte[] buffer = new byte[150];
        
        for (int i = 0; i < buffer.length; i++) 
        {
            buffer[i] = (byte) (Math.random() * 61 + 20);
        }
        
        return buffer;
    }
    
    private static void visializarContenido(File file)
    {
        try (DataInputStream reader = new DataInputStream(new FileInputStream(file)))
        {
            while (true)
            {
                int num = reader.read();

                if (num == -1) return;

                System.out.println("Numero leido: " + num);
            }
        }
        catch (Exception e)
        {
            System.out.println("Unrecheable");
        }
    }
}
