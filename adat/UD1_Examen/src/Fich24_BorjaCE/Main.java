package Fich24_BorjaCE;

import Fich24_BorjaCE.data.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Main
{
    private static final File DATOS_COCINEROS_TXT = new File("DatosCocineros.txt");

    public static void main(String[] args)
    {
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Se van a insertar 4 cocineros usando llamadas al metodo de insercion");
        // Inserción de 4 cocineros
        CocineroDAO.insertar("Zoco", "Borja Castellano-Extremadouro",
                LocalDate.of(2000, 6, 10), "Lasaña", "Polvo a feira");
        CocineroDAO.insertar("Casi", "Casiano Barrio Rodríguez",
                LocalDate.of(2000, 8, 6), "Tortilla española");
        CocineroDAO.insertar("Trendavi", "David Castellano-Extremadouro",
                LocalDate.of(2004, 11, 11), "Huevos revueltos", "Rape a la plancha");
        CocineroDAO.insertar("Uxi", "Uxia Camiña Gonzalez",
                LocalDate.of(2000, 1, 21), "Tortilla francesa", "Arroz con marisco");

        System.out.println("--------------------------------------------------------------------");
        System.out.println("Se va a insertar un cocinero con un apodo repetido para comprobar que de el mensaje de " +
                "error correspondiente");
        // Inserción de un repetido
        CocineroDAO.insertar("Uxi", "Uxia Camiña Gonzalez",
                LocalDate.of(2000, 8, 6), "Tortilla española");

        System.out.println("--------------------------------------------------------------------");
        System.out.println("Se va a visualizar la informacion de todos los registros");
        CocineroDAO.visualizarTodaInformacion();

        System.out.println("--------------------------------------------------------------------");
        System.out.println("Se van a insertar cocineros desde un archivo de texto con un formato dado");
        // Inserción de los cocineros encontrados en el archivo DatosCocineros.dat
        CocineroDAO.insertar(DATOS_COCINEROS_TXT);

        System.out.println("--------------------------------------------------------------------");
        System.out.println("Se va a visualizar la informacion de todos los registros");
        CocineroDAO.visualizarTodaInformacion();

        System.out.println("--------------------------------------------------------------------");
        System.out.println("Se van a insertar restaurantes");
        // Insercion de Restaurantes
        InfoCocinero infoCocinero1 = new InfoCocinero(3, "Chef");
        InfoCocinero infoCocinero2 = new InfoCocinero(1, "Jefe Cocina");
        InfoCocinero infoCocinero3 = new InfoCocinero(13, "Cocinero Internacional");
        InfoCocinero infoCocinero4 = new InfoCocinero(16, "Cocinero Salsas");
        InfoCocinero infoCocinero5 = new InfoCocinero(Integer.MAX_VALUE, "Cocinero Salsas");


        RestauranteDAO.insertar("Romasa", "Arcade", infoCocinero1, infoCocinero2);

        RestauranteDAO.insertar("Beiramar", "Arcade", infoCocinero1);

        RestauranteDAO.insertar("El Anzuelo", "Arcade", infoCocinero1, infoCocinero3);

        RestauranteDAO.insertar("Hamburguesería la creación", "Arcade", infoCocinero1);

        RestauranteDAO.insertar("Anden 3/4", "Arcade", infoCocinero3, infoCocinero1, infoCocinero4);

        // Insercion de dos cocinero donde uno no es valido
        RestauranteDAO.insertar("Arcadia", "Arcade", infoCocinero1, infoCocinero5);

        System.out.println("--------------------------------------------------------------------");
        System.out.println("Se va a insertar restaurante repetido para comprobar que de el error correspondiente");
        RestauranteDAO.insertar("Romasa", "Arcade", infoCocinero1);

        System.out.println("--------------------------------------------------------------------");
        System.out.println("Se va a visualizar la informacion de todos los restaurantes registrados");
        RestauranteDAO.visualizarTodaInformacion();

        System.out.println("---------------------------------------------------------------------");
        System.out.println("Se van a aguardar los cocineros por edad en un archivo de texto CocinerosporEdad.txt");
        // Guardamos los cocinero por edad dentro de un archivo con un formato especifico
        guardarCocineroPorEdad();

        System.out.println("--------------------------------------------------------------------");
        System.out.println("Mostrar todos los datos de un restaurante y permitir borrar un cocinero");
        // El metodo muestra, permite borrar y guarda en su fichero correspondiente la informacion modificada
        eliminarCocineroDeUnRestaurante(1);

        System.out.println("-------------------------------------------------------------------");
        System.out.println("Mostramos toda la informacion de los registros guardados de los restaurantes");
        RestauranteDAO.visualizarTodaInformacion();
    }

    private static final File COCINEROS_POR_EDAD_TXT = new File("CocinerosporEdad.txt");

    private static void guardarCocineroPorEdad()
    {
        TreeMap<Integer, List<Cocinero>> cocinerosPorEdad = CocineroDAO.obtenerCocinerosPorEdad();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COCINEROS_POR_EDAD_TXT)))
        {
            for (var entry : cocinerosPorEdad.entrySet())
            {
                writer.write("Cocineros con edad " + entry.getKey() + "\n");
                writer.write(String.format("%-8s %s\n", "Código", "Apodo (Nombre)"));

                for (var cocinero : entry.getValue())
                {
                    writer.write(String.format("%-8d %s (%s)\n", cocinero.getCodigo(), cocinero.getApodo(),
                            cocinero.getNombreCompleto()));
                }
                
                writer.write("------------------------------------\n");
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error: No se ha encontrado el fichero");
        }
        catch (IOException e)
        {
            System.out.println("Error de entrada salida");
        }
    }
    
    private static void eliminarCocineroDeUnRestaurante(int id)
    {
        Restaurante restaurante = RestauranteDAO.leer(id);
        
        if (restaurante == null || restaurante.isBaja()) 
        {
            System.out.println("No se ha encontrado ningun restaurante con el id dado");
            return;
        }

        System.out.println(restaurante.preattyString());

        System.out.println("Introduzca el id de algun cocinero: ");
        int idCocinero = new Scanner(System.in).nextInt();

        System.out.println("Introduzca 'B' para borrar el cocinero o cualquier otra letra para cancelar");
        
        if (!new Scanner(System.in).next().equals("B")) 
        {
            System.out.println("Se ha cancelado la operacion");
            return;
        }
        
        restaurante.getCocineros().removeIf(infoCocinero -> infoCocinero.getCodigo() == idCocinero);
        restaurante.setNumcocineros(restaurante.getCocineros().size());
        RestauranteDAO.modificar(restaurante);
    }
}