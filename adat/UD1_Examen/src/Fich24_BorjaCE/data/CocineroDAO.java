package Fich24_BorjaCE.data;

import Fich24_BorjaCE.CustomObjectOutputStream;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CocineroDAO
{
    private static final File DAT_FILE = new File("Cocineros.dat");
    
    // Inserta datos a partir de un archivo dado. Este archivo debe tener un formato determinado
    public static boolean insertar(File dataFile)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile)))
        {
            String line;
            
            while ((line = reader.readLine()) != null) 
            {
                String[] datos = line.split(",");
                
                String apodo = datos[0];
                String nombre = datos[1];
                LocalDate fechaNacimiento = LocalDate.parse(datos[2], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                List<String> recetas = new ArrayList<>(Arrays.asList(datos).subList(3, datos.length));
                
                if (!insertar(apodo, nombre, fechaNacimiento, recetas)) return false;
            }
        
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No se pudo insertar al cocinero: No se ha encontrado el fichero de texto");
        }
        catch (IOException e)
        {
            System.out.println("No se pudo insertar el cocinero: Error de E/S");
        }

        return true;
    }
    
    // Inserta un objeto Cocinero ya introducido con un id dado previamente
    private static boolean insertar(Cocinero cocinero)
    {
        try (ObjectOutputStream objectOutputStream = DAT_FILE.exists() && getNumRegistros() >= 1 
                ? new CustomObjectOutputStream(new FileOutputStream(DAT_FILE, true)) 
                : new ObjectOutputStream(new FileOutputStream(DAT_FILE)))
        {
           objectOutputStream.writeObject(cocinero);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No se pudo insertar al cocinero: No se ha encontrado el fichero");
        }
        catch (IOException e)
        {
            System.out.println("No se pudo insertar el cocinero: Error de E/S");
        }
        
        return true;
    }

    // Permite insertar un cocinero dado los datos y no el objeto mismo. Recibe una lista de recetas 
    public static boolean insertar(String apodo, String nombreCompleto, LocalDate fechaNacimiento, List<String> recetas)
    {
        if (!esInsertable(apodo))
        {
            System.out.println("No se pudo insertar al cocinero con apodo " + apodo + " porque ya está en uso por " +
                    "otro registro");
            return false;
        }

        int idActual = getNumRegistros();
        int siguienteId = idActual + 1;

        numeroRegistros = siguienteId;
        
        Cocinero cocinero = new Cocinero(siguienteId, apodo, nombreCompleto, fechaNacimiento, recetas);

        return insertar(cocinero);
    }

    // Permite insertar un cocinero dado los datos y no el objeto mismo. Recibe varias recetas o un array de estas
    public static boolean insertar(String apodo, String nombreCompleto, LocalDate fechaNacimiento, String... recetas)
    {
        return insertar(apodo, nombreCompleto, fechaNacimiento, List.of(recetas));
    }
    
    // Escribe toda la informacion de los cocineros registrados por consola
    public static void visualizarTodaInformacion()
    {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DAT_FILE)))
        {
            while (true)
            {
                System.out.println(((Cocinero) inputStream.readObject()).preattyString());
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
        catch (ClassNotFoundException e)
        {
            System.out.println("No se pudo visualizar la informacion del fichero de datos: Error de deserialización");
        }
    }
    
    // Devuelve verdadero si un apodo no esta en uso por algun registro
    private static boolean esInsertable(String apodo)
    {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DAT_FILE)))
        {
            while (true)
            {
                Cocinero cocineroLeido = (Cocinero) inputStream.readObject();
                
                if (apodo.equals(cocineroLeido.getApodo())) 
                {
                    return false;
                }
            }
        }
        catch (EOFException eofException)
        {
            // Excepcion lanzada al terminar el fichero
        }
        catch (FileNotFoundException e)
        {
            // Si no existe el fichero es insertable si o si ya que eaun no existen registros
        }
        catch (IOException e)
        {
            System.out.println("No se pudo leer la informacion del fichero de datos: Error de E/S");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("No se pudo leer la informacion del fichero de datos: Error de deserialización");
        }
        
        return true;
    }
    
    // Permite leer dado un id
    public static Cocinero leer(int id)
    {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DAT_FILE)))
        {
            for (int i = 1; i < id; i++) 
            {
                inputStream.readObject();
            }

            return (Cocinero) inputStream.readObject();
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
        catch (ClassNotFoundException e)
        {
            System.out.println("No se pudo visualizar la informacion del fichero de datos: Error de deserialización");
        }
        
        return null;
    }

    // Devuelve un mapa ordenado de los cocineros agrupandolos por edad y ordenandolos por esta
    public static TreeMap<Integer, List<Cocinero>> obtenerCocinerosPorEdad()
    {
        TreeMap<Integer, List<Cocinero>> mapaEdades = new TreeMap<>();
        
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DAT_FILE)))
        {
            while (true)
            {
                Cocinero cocineroLeido = (Cocinero) inputStream.readObject();
                mapaEdades.computeIfAbsent(cocineroLeido.getEdad(), a -> new ArrayList<>()).add(cocineroLeido);
            }
        }
        catch (EOFException eofException)
        {
            // Excepcion lanzada al terminar el fichero
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No se pudo leer la informacion del fichero de datos: Fichero no encontrado");
        }
        catch (IOException e)
        {
            System.out.println("No se pudo leer la informacion del fichero de datos: Error de E/S");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("No se pudo leer la informacion del fichero de datos: Error de deserialización");
        }
        
        return mapaEdades;
    }
    
    // region: Gestion de los Ids
    
    private static int numeroRegistros = -1;
    
    // Devuelve el numero de registros del fichero
    public static int getNumRegistros()
    {
        if (numeroRegistros != -1) return numeroRegistros;
        
        if (!DAT_FILE.exists())
        {
            numeroRegistros = 0;
            return numeroRegistros;
        }
        
        int contador = 0;
        
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(DAT_FILE)))
        {
            while (input.readObject() != null) 
            {
                contador++;
            }
        }
        catch (EOFException ignored) 
        {
            // Excepcion lanzada al terminar el fichero
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No se pudo obtener el numero de registros: Fichero no encontrado");
        }
        catch (IOException e)
        {
            System.out.println("No se pudo obtener el numero de registros: Error de E/S");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("No se pudo obtener el numero de registros: Error de deserialización");
        }

        numeroRegistros = contador;
        return numeroRegistros;
    }
    
    // endregion
}
