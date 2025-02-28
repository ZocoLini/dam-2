package org.lebastudios.restapiclientexamen;

import org.lebastudios.restapiclientexamen.httpbodies.Pepe;
import org.lebastudios.restapiclientexamen.httpbodies.URLEncoder;

import java.util.Scanner;

public class Main
{
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        System.out.println(URLEncoder.encode(new Pepe("ad", "bn")));
        
        appMenu();
    }
    
    private static void appMenu()
    {
        boolean exit = false;
    
        while (!exit)
        {
            switch (showMenu())
            {
                case 1 -> obtenerDatosDeUnOperador();
                case 2 -> insertarNuevoTelefono();
                case 3 -> visualizarTelefonoDeUnTitular();
                case 4 -> cambiarDeOperador();
                case 5 -> mostrarHistorialDeCambios();
                default -> exit = true;
            }
        }

        System.out.println("Gracias por la visita a nuestra nueva terminal de consola :D");
    }

    private static int showMenu()
    {
        System.out.println("----  Menu  ----");
        System.out.println("   1.- Obtener datos de un operador:");
        System.out.println("   2.- Insertar un nuevo telefono");
        System.out.println("   3.- Visualizar telefonos de un titular en concreto");
        System.out.println("   4.- Cambiar de operador");
        System.out.println("   5.- Historial de cambios de un telefono dado");
        System.out.println();
        
        try
        {
            return scanner.nextInt();
        }
        catch (Exception ignore)
        {
            return -1;
        }
    }

    private static void mostrarHistorialDeCambios() 
    {
    }

    private static void visualizarTelefonoDeUnTitular() {}

    private static void cambiarDeOperador() {}

    private static void insertarNuevoTelefono() {}

    private static void obtenerDatosDeUnOperador() 
    {
        System.out.println("Seleccione alguno de los operadores anteriores:");
        int codOperador = scanner.nextInt();
    }
}
