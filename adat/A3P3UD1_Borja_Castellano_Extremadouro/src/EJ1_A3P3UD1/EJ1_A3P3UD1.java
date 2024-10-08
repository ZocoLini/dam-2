package EJ1_A3P3UD1;

import java.io.*;
import java.util.Scanner;

public class EJ1_A3P3UD1
{
    private static final String DAT_FILE_NAME = "corredores.dat";
    private static final File DAT_FILE = new File(DAT_FILE_NAME);

    public static final String AUX_FILE_NAME = "corredores.tmp";
    public static final File AUX_FILE = new File(AUX_FILE_NAME);

    private static int dorsal = 1;
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
        } catch (Exception ignore)
        {
        }

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
            
            DAT_FILE.delete();
        }
        
        try (FileOutputStream writer = new FileOutputStream(DAT_FILE)) {} catch (Exception _) { }
    }

    private static void addRegistro()
    {
        String line = new Scanner(System.in).nextLine();

        ObjectOutputStream writer = null;

        try
        {
            writer = DAT_FILE.exists()
                    ? new CustomObjectOutputStream(new FileOutputStream(DAT_FILE, true))
                    : new ObjectOutputStream(new FileOutputStream(DAT_FILE));

            Corredor corredor = new Corredor(line, dorsal, false);
            dorsal++;
            writer.writeObject(corredor);
        } catch (Exception exception)
        {
            System.out.println("El formato introducido no ha sido el correcto");
            System.out.println("ERROR: " + exception.getMessage());
        }
        finally
        {
            if (writer != null)
            {
                try
                {
                    writer.close();
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void consultarRegistro()
    {
        System.out.println("Introduce el dorsal del corredor que quieres consultar: ");
        int dorsal = new Scanner(System.in).nextInt();

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DAT_FILE)))
        {
            Corredor corredor;

            while ((corredor = (Corredor) reader.readObject()) != null)
            {
                if (corredor.dorsal() == dorsal)
                {
                    System.out.println(corredor);
                    return;
                }
            }

            System.out.println("No se ha encontrado ningún corredor con el dorsal " + dorsal);
        } catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }

    private static void consultarTodosRegistros()
    {
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DAT_FILE)))
        {
            Corredor corredor;

            while ((corredor = (Corredor) reader.readObject()) != null)
            {
                System.out.println(corredor);
            }
        } catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }

    private static void modRegistro()
    {
        System.out.println("Introduce el dorsal del corredor que quieres modificar: ");
        int dorsal = new Scanner(System.in).nextInt();

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DAT_FILE));
             ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(AUX_FILE)))
        {
            Corredor corredor;

            while ((corredor = (Corredor) reader.readObject()) != null)
            {
                if (corredor.dorsal() == dorsal)
                {
                    System.out.println("Introduce el nuevo nombre del corredor: ");
                    String name = new Scanner(System.in).nextLine();
                    System.out.println("Introduce los nuevos segundos del corredor: ");
                    int seconds = new Scanner(System.in).nextInt();

                    corredor = new Corredor(name + " " + seconds, dorsal, false);
                }

                writer.writeObject(corredor);
            }

            DAT_FILE.delete();
            AUX_FILE.renameTo(DAT_FILE);
        } catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }

    private static void borrar()
    {
        System.out.println("Introduce el dorsal del corredor que quieres modificar: ");
        int dorsal = new Scanner(System.in).nextInt();

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(DAT_FILE));
             ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(AUX_FILE)))
        {
            Corredor corredor;

            while ((corredor = (Corredor) reader.readObject()) != null)
            {
                if (corredor.dorsal() == dorsal)
                {
                    continue;
                }

                writer.writeObject(corredor);
            }

            DAT_FILE.delete();
            AUX_FILE.renameTo(DAT_FILE);
        } catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
    }

    private static boolean obtenerConfirmacion()
    {
        try
        {
            char response = new Scanner(System.in).nextLine().charAt(0);

            return response == 'Y' || response == 'y';
        } catch (IndexOutOfBoundsException exception)
        {
            return true;
        }
    }
}
