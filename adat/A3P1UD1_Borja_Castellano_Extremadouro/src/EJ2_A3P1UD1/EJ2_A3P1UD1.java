package EJ2_A3P1UD1;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EJ2_A3P1UD1
{
    public static void main(String[] args)
    {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        File file = new File("data.txt");

        if (file.exists())
        {
            System.out.println("El archivo ya existe. ¿Quiere remplazarlo? (Y/n)");
            if (!scanner.nextLine().isBlank() || scanner.nextLine().charAt(0) == 'N')
            {
               return;
            }
        }
        
        while (!exit) 
        {
            try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(file)))
            {
                int num = scanner.nextInt();
                writer.writeInt(num);
            }
            catch (InputMismatchException e)
            {
                exit = true;
            }
            catch (FileNotFoundException e)
            {
                System.out.println("No se encontro el fichero: " + file.getAbsolutePath());
                exit = true;
            }
            catch (IOException e)
            {
                System.out.println("Problema de entrada salida de datos");
                exit = true;
            }
        }
        
        try (DataInputStream reader = new DataInputStream(new FileInputStream(file)))
        {
            while (true) 
            {
                int num = reader.readInt();
                
                System.out.println("Numero leido: " + num);
            }
        }
        catch (EOFException e)
        {
            System.out.println("EOF");
        }
        catch (Exception e)
        {
            System.out.println("Unrecheable");
        }
    }
}
