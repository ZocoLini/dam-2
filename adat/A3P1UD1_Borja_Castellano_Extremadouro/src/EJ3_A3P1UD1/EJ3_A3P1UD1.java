package EJ3_A3P1UD1;

import java.io.*;

public class EJ3_A3P1UD1
{
    public static void main(String[] args)
    {
        File file1 = new File("Num1.dat");
        File file2 = new File("Num2.dat");
        File mezcla = new File("Mezcla.dat");
        
        generarFicheroOrdenado(new File("Num1.dat"));
        generarFicheroOrdenado(new File("Num2.dat"));
        
        mezclarFicheros(file1, file2, mezcla);
    }
    
    private static void mezclarFicheros(File file1, File file2, File mezcla)
    {
        try (DataInputStream reader1 = new DataInputStream(new FileInputStream(file1));
             DataInputStream reader2 = new DataInputStream(new FileInputStream(file2));
             DataOutputStream writer = new DataOutputStream(new FileOutputStream(mezcla)))
        {
            try
            {
                int num1 = reader1.readInt();
                int num2 = reader2.readInt();
                
                while (true) 
                {
                    if (num1 > num2) 
                    {
                        writer.writeInt(num2);
                        System.out.println(num2);
                        num2 = reader2.readInt();
                    }
                    else
                    {
                        writer.writeInt(num1);
                        System.out.println(num1);
                        num1 = reader1.readInt();
                    }
                }
            }
            catch (EOFException exception) {}
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Not file found");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void generarFicheroOrdenado(File file)
    {
       try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(file)))
       {
           for (int i = 0; i < 100;)
           {
               if (Math.random() > 0.50)
               {
                   i++;
                   continue;
               }
               
               
               writer.writeInt(i);
           }
       }
       catch (Exception exception)
       {
       
       }
    }
}
