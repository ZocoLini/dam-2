package EJ1_A3P3UD1;

import java.io.*;
import java.util.Scanner;

public class EJ1_A3P3UD1
{
    private static final File DAT_FILE = new File("corredores.dat");

    public static void main(String[] args)
    {
        boolean exit = false;

        while (!exit)
        {
            switch (showMenu())
            {
                case 1 -> crearArchivo();
                case 2 -> addRegistro();
                case 3 -> consultarRegistro();
                case 4 -> consultarTodosRegistros();
                case 5 -> modRegistro();
                case 6 -> borrar();
                case 7 -> exit = true;
                default -> System.out.println("El valor introducido no es valido");
            }
        }
    }

    private static int showMenu()
    {
        System.out.println("Menú de opciones");
        System.out.println("----------------");
        System.out.println(" 1.-Crear archivo");
        System.out.println(" 2.-Añadir registros");
        System.out.println(" 3.-Consultar un registro");
        System.out.println(" 4.-Consultar todos los registros");
        System.out.println(" 5.-Modificar un registro");
        System.out.println(" 6.-Borrar");
        System.out.println(" 7.-Salir");
        System.out.println("Elige una opción <1-7>");

        int response = -1;

        try
        {
            response = new Scanner(System.in).nextInt();
        }
        catch (Exception ignore) {}

        return response;
    }

    private static void crearArchivo()
    {
        if (DAT_FILE.exists())
        {
            System.out.println("Se ha encontrado un fichero de datos. Desea remplazarlo? (Y/n)");

            if (!obtenerConfirmacion())
            {
                System.out.println("Cancelando la operación");
                return;
            }
        }
        
        
    }

    private static void addRegistro()
    {
        String line = new Scanner(System.in).nextLine();
        
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(DAT_FILE, true)))
        {
            Corredor corredor = new Corredor(line);
            writer.writeObject(corredor);
        }
        catch (Exception exception)
        {
            System.out.println("El formato introducido no ha sido el correcto");
            System.out.println("ERROR: " + exception.getMessage());
        }
    }

    private static void consultarRegistro() {}

    private static void consultarTodosRegistros() 
    {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DAT_FILE)))
        {
            Corredor corredor;
            
            while ((corredor = (Corredor) reader.readObject()) != null) 
            {
                System.out.println(corredor);
            }
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }

    private static void modRegistro() {}

    private static void borrar() {}

    private static boolean obtenerConfirmacion()
    {
        try
        {
            char response = new Scanner(System.in).nextLine().charAt(0);

            return response == 'Y' || response == 'y';
        }
        catch (IndexOutOfBoundsException exception)
        {
            return true;
        }
    }
}
