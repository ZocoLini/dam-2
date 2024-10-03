package EJ1_A3P4UD1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class EJ1_A3P4UD1
{
    public static final int REGISTER_SIZE = 80;
    private static final String DAT_FILE_NAME = "corredores.dat";
    private static final File DAT_FILE = new File(DAT_FILE_NAME);

    public static void main(String[] args)
    {
        boolean exit = false;

        while (!exit)
        {
            switch (showMenu())
            {
                case 1 -> addRegistro();
                case 2 -> consultarRegistro();
                case 3 -> consultarTodosRegistros();
                case 4 -> modRegistro();
                case 5 -> borrar();
                case 6 -> exit = true;
                default -> System.out.println("El valor introducido no es valido");
            }
        }
    }

    private static int showMenu()
    {
        System.out.println("Menú de opciones");
        System.out.println("----------------");
        System.out.println(" 1.-Añadir registros");
        System.out.println(" 2.-Consultar un registro");
        System.out.println(" 3.-Consultar todos los registros");
        System.out.println(" 4.-Modificar un registro");
        System.out.println(" 5.-Borrar");
        System.out.println(" 6.-Salir");
        System.out.println("Elige una opción <1-6>");

        int response = -1;

        try
        {
            response = new Scanner(System.in).nextInt();
        }
        catch (Exception ignore)
        {
        }

        return response;
    }

    private static void addRegistro()
    {
        System.out.println("Añadiendo registros hasta que introduzcas * o dejes el nombre vacio");
        try (RandomAccessFile raf = new RandomAccessFile(DAT_FILE, "rw"))
        {
            boolean exit = false;
            while (!exit)
            {
                Corredor corredor = crearCorredor();

                if (corredor == null)
                {
                    exit = true;
                    continue;
                }

                writeCorredor(corredor, raf);
            }
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

    private static Corredor crearCorredor()
    {
        return crearCorredor(getNumeroRegistros() + 1);
    }

    private static Corredor crearCorredor(int dorsal)
    {
        System.out.println("Introduce el nombre del corredor: ");
        String nombre = new Scanner(System.in).nextLine();

        if (nombre.equals("*") || nombre.isEmpty())
        {
            return null;
        }

        System.out.println("Introduce el tiempo del corredor en segundos: ");
        int tiempo = new Scanner(System.in).nextInt();

        return new Corredor(nombre, dorsal, tiempo, false);

    }

    private static void writeCorredor(Corredor corredor, RandomAccessFile raf) throws IOException
    {
        var bytes = corredor.toString().getBytes();

        if (bytes.length > REGISTER_SIZE)
        {
            throw new RuntimeException("El registro es demasiado grande");
        }

        raf.write(bytes);
        raf.write(new byte[REGISTER_SIZE - bytes.length]);
    }

    private static void consultarRegistro()
    {
        System.out.println("Introduce el dorsal del corredor que quieres consultar: ");
        int dorsal = new Scanner(System.in).nextInt();

        if (dorsal > getNumeroRegistros())
        {
            throw new IndexOutOfBoundsException();
        }

        try (RandomAccessFile raf = new RandomAccessFile(DAT_FILE, "rw"))
        {
            raf.seek((long) (dorsal - 1) * REGISTER_SIZE);
            
            Corredor corredor = leerCorredor(raf);

            if (corredor.borrado())
            {
                System.out.println("Este registro está marcado como borrado");
                return;
            }

            System.out.println(corredor);
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

    private static void consultarTodosRegistros()
    {
        System.out.println("Mostrando todos los registros");

        try (RandomAccessFile raf = new RandomAccessFile(DAT_FILE, "r"))
        {
            for (int i = 0; i < getNumeroRegistros(); i++)
            {
                raf.seek((long) i * REGISTER_SIZE);
                Corredor corredor = leerCorredor(raf);

                if (corredor.borrado())
                {
                    continue;
                }

                System.out.println(corredor);
            }
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

    private static Corredor leerCorredor(RandomAccessFile raf) throws IOException
    {
        byte[] bytes = new byte[REGISTER_SIZE];
        raf.read(bytes);

        int end = 0;

        for (byte data : bytes)
        {
            if (data == 0)
            {
                break;
            }

            end++;
        }

        byte[] by = new byte[end];
        System.arraycopy(bytes, 0, by, 0, end);

        return Corredor.fromString(new String(by));
    }

    private static void modRegistro()
    {
        System.out.println("Introduce el dorsal del corredor que quieres modificar: ");
        int dorsal = new Scanner(System.in).nextInt();

        if (dorsal > getNumeroRegistros())
        {
            throw new IndexOutOfBoundsException();
        }

        try (RandomAccessFile raf = new RandomAccessFile(DAT_FILE, "rw"))
        {
            raf.seek((long) (dorsal - 1) * REGISTER_SIZE);
            Corredor corredor = crearCorredor(dorsal);

            if (corredor == null)
            {
                System.out.println("Se ha cancelado la modificación del registro");
                return;
            }

            writeCorredor(corredor, raf);

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

    private static void borrar()
    {
        System.out.println("Introduce el dorsal del corredor que quieres modificar: ");
        int dorsal = new Scanner(System.in).nextInt();

        try (RandomAccessFile raf = new RandomAccessFile(DAT_FILE, "rw"))
        {
            raf.seek((long) (dorsal - 1) * REGISTER_SIZE);
            Corredor corredor = leerCorredor(raf);

            Corredor newCorredor = new Corredor(corredor.nombre(),
                    corredor.dorsal(), corredor.tiempo(), true);

            raf.seek((long) (dorsal - 1) * REGISTER_SIZE);
            writeCorredor(newCorredor, raf);
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

    private static int getNumeroRegistros()
    {
        return (int) Math.ceilDiv(DAT_FILE.length(), REGISTER_SIZE);
    }
}
