package Fich24_BorjaCE.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RestauranteDAO
{
    private static final File DAT_FILE = new File("Restaurantes.dat");
    private static final int MAX_BYTES_REGISTRO = 200;

    // Permite insertar un restaurante dados sus datos
    public static boolean insertar(String nombre, String localidad, InfoCocinero... cocineros)
    {
        Restaurante restaurante = new Restaurante(getNumeroRegistros() + 1, nombre, localidad,
                List.of(cocineros), false);
        
        return insertar(restaurante);
    }

    // Permite insertar un restaurante dado el objeto. El restaurante debe tener definido el id
    private static boolean insertar(Restaurante restaurante)
    {
        if (restaurante.getLongitudRegistro() > MAX_BYTES_REGISTRO) 
        {
            System.out.println("La longitud del registro es demasiado grande y no se ha podido insertar.");
            return false;
        }
        
        if (!esInsertable(restaurante))
        {
            System.out.println(
                    "Se ha intentado insertar un registro que tiene un nombre en uso por otro restaurante");
            return false;
        }
        
        return modificar(restaurante);
    }

    // Permite guardar un restaurante en su posicion segun id sin comprobar si existe otro con el mismo apodo. Esta 
    // comprobacion debe ser hecha previamente
    public static boolean modificar(Restaurante restaurante)
    {
        try (RandomAccessFile raf = new RandomAccessFile(DAT_FILE, "rw"))
        {
            raf.seek((long) (restaurante.getNumero() - 1) * MAX_BYTES_REGISTRO);

            raf.writeInt(restaurante.getNumero());
            raf.writeUTF(restaurante.getNombre());
            raf.writeUTF(restaurante.getLocalidad());

            // Contar los cocineros validos
            List<InfoCocinero> cocinerosValidos = new ArrayList<>();
            
            for (var infoCocinero : restaurante.getCocineros())
            {
                if (CocineroDAO.leer(infoCocinero.getCodigo()) == null)
                {
                    System.out.println("Se ha intentado añadir al restaurante un cocinero de codigo " + infoCocinero.getCodigo()
                            + " pero este registro no esta presente en el archivo de datos de los cocineros. Se ignorará " +
                            "este cocinero");
                    continue;
                }
                
                cocinerosValidos.add(infoCocinero);
            }

            // Escribir los cocineros validos
            
            restaurante.setNumcocineros(cocinerosValidos.size());
            raf.writeInt(restaurante.getNumcocineros());
            
            for (var infoCocinero : cocinerosValidos)
            {
                InfoCocineroDAO.insertar(infoCocinero, raf);
            }
            
            raf.writeBoolean(restaurante.isBaja());
            return true;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error en el registro de Restaurantes: No se ha encontrado el archivo" + DAT_FILE.getName());
        }
        catch (IOException e)
        {
            System.out.println("Error de E/S en el registro de Restaurantes");
        }

        return false;
    }

    // Permite leer un restaurante dado su id y un objeto de tipo RandomAccessFile que acceda al fichero correspondiente
    private static Restaurante leer(int i, RandomAccessFile raf)
    {
        try
        {
            raf.seek((long) (i - 1) * MAX_BYTES_REGISTRO);
            
            int numero = raf.readInt();
            String nombre = raf.readUTF();
            String localidad = raf.readUTF();
            int numCocineros = raf.readInt();
            
            ArrayList<InfoCocinero> infoCocineros = new ArrayList<>();
            
            for (int j = 0; j < numCocineros; j++) 
            {
                infoCocineros.add(InfoCocineroDAO.leer(raf));
            }
            
            boolean baja = raf.readBoolean();
            
            return new Restaurante(numero, nombre, localidad, infoCocineros, baja);
        }
        catch (IOException e)
        {
            System.out.println("Error de E/S al intentar leer el archivo " + DAT_FILE.getName());
        }
        
        return null;
    }

    // Permite leer un Restaurante dado su id
    public static Restaurante leer(int i)
    {
        try (RandomAccessFile raf = new RandomAccessFile(DAT_FILE, "r"))
        {
            return leer(i, raf);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error de lectura: El archivo " + DAT_FILE.getName() + " no se ha encontrado");
        }
        catch (IOException e)
        {
            System.out.println("Error de E/S al intentar leer el archivo " + DAT_FILE.getName());
        }
        
        return null;
    }

    // Comprueba si un restaurante puede ser o no insertado
    private static boolean esInsertable(Restaurante restaurante)
    {
        try (RandomAccessFile raf = new RandomAccessFile(DAT_FILE, "r"))
        {
            for (int i = 0; i < getNumeroRegistros(); i++)
            {
                Restaurante restauranteLeido = leer(i + 1, raf);

                if (restauranteLeido == null) 
                {
                    throw new IllegalStateException("Nunca deberia suceder");
                }
                
                if (restauranteLeido.isBaja()) continue;

                if (restaurante.getNombre().equals(restauranteLeido.getNombre()))
                {
                    return false;
                }
            }
        }
        catch (FileNotFoundException e) {}
        catch (IOException e)
        {
            System.out.println("Error de E/S al intentar leer el archivo " + DAT_FILE.getName());
            return false;
        }
        return true;
    }

    // Visualiza la informacion de todos los registros de restaurantes
    public static void visualizarTodaInformacion()
    {
        try (RandomAccessFile raf = new RandomAccessFile(DAT_FILE, "r"))
        {
            for (int i = 0; i < getNumeroRegistros(); i++)
            {
                Restaurante restaurante = leer(i + 1, raf);

                if (restaurante == null)
                {
                    throw new IllegalStateException("Nunca deberia suceder");
                }

                if (restaurante.isBaja()) continue;

                System.out.println(restaurante.preattyString());
            }
        }
        catch (EOFException eofException)
        {
            // Excepcion lanzada al terminar el fichero
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No se pudo visualizar la informacion del fichero de datos: Fichero no encontrado");
        }
        catch (IOException e)
        {
            System.out.println("No se pudo visualizar la informacion del fichero de datos: Error de E/S");
        }
    }
    
    // Obtiene el numero de restaurantes registrados
    private static int getNumeroRegistros()
    {
        if (!DAT_FILE.exists()) return 0;

        return (int) Math.ceilDiv(DAT_FILE.length(), MAX_BYTES_REGISTRO);
    }
}
