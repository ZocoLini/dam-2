import java.io.*;

public class EJ4_A3P1UD1
{
    public static void main(String[] args)
    {
        File file = new File("Mezcla.dat");
        contarRepes(file);
    }
    
    private static void contarRepes(File file)
    {
        try (DataInputStream reader = new DataInputStream(new FileInputStream(file)))
        {
            int previusNumber = reader.readInt();
            int num;
            int contador = 1;
            
            while ((num = reader.readInt()) != -1) 
            {
                if (num == previusNumber) 
                {
                    contador++;
                }
                else
                {
                    System.out.println("El numero " + previusNumber + " apareció " + contador + " veces.");
                    contador = 1;
                }
                
                previusNumber = num;
            }
        }
        catch (EOFException exception)
        {
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
