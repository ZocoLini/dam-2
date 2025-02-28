package org.lebastudios.restapiclientexamen;

import org.lebastudios.restapiclientexamen.entities.Operador;
import org.lebastudios.restapiclientexamen.entities.Telefono;
import org.lebastudios.restapiclientexamen.http.HttpMethods;
import org.lebastudios.restapiclientexamen.httpbodies.Pepe;
import org.lebastudios.restapiclientexamen.httpbodies.URLEncoder;

import java.util.Arrays;
import java.util.Scanner;

public class Main
{
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
            return new Scanner(System.in).nextInt();
        }
        catch (Exception ignore)
        {
            return -1;
        }
    }

    private static void pause()
    {
        System.out.println("Presiona cualquier tecla para continuar...");
        new Scanner(System.in).nextLine();
    }

    private static void mostrarHistorialDeCambios()
    {
    }

    private static void visualizarTelefonoDeUnTitular() {}

    private static void cambiarDeOperador() {}

    private static void insertarNuevoTelefono()
    {
        System.out.println("Introduzca el numero del nuevo telefono que quiere registrar: ");
        String numeroDelTelefono = new Scanner(System.in).nextLine();

        if (!numeroDelTelefono.matches("\\d{0,9}"))
        {
            System.out.println("El numero de telefnono ha de estar compuesto de 9 o menos caracteres numéricos");
            pause();
            return;
        }

        System.out.println("Introduzca a que operadora pertenece el telefono: ");
        String numeroOperadora = new Scanner(System.in).nextLine();

        if (!numeroOperadora.matches("\\d+"))
        {
            System.err.println("Ha introducido una operadora no valida. Solo caracteres numéricos");
            pause();
            return;
        }

        System.out.println("Introduzca el nombre del titular");
        String titular = new Scanner(System.in).nextLine();

        Telefono telefono = new Telefono(numeroDelTelefono, Integer.parseInt(numeroOperadora), titular);

        boolean result = HttpMethods.post("telefonos", telefono);

        System.out.println(result
                ? "El teléfono ha sido insertado de forma satisfactoria"
                : "Error durante la inserción del nuevo teléfono, compruebe que los datos sean validos y que no " +
                "exista ya en la base de datos");

        pause();
    }

    private static void obtenerDatosDeUnOperador()
    {
        Operador[] operadores = HttpMethods.get("operadores", Operador[].class);

        if (operadores == null)
        {
            System.err.println("Error en la petición al servidor");
            pause();
            return;
        }

        Arrays.stream(operadores).forEach(operador ->
        {
            System.out.printf("%d - %s\n", operador.getCodOperador(), operador.getNombre());
        });

        System.out.println("Seleccione alguno de los operadores anteriores:");
        int codOperador = new Scanner(System.in).nextInt();

        Telefono[] telefonos = HttpMethods.get("telefonos?codOperador=" + codOperador, Telefono[].class);

        if (telefonos == null)
        {
            System.err.println("Error en la petición al servidor");
            pause();
            return;
        }

        System.out.println("Telefonos");
        Arrays.stream(telefonos).forEach(tel ->
        {
            System.out.printf("Numero: %s - %s (Operadora: %d)\n", tel.getNumero(), tel.getTitular(),
                    tel.getCodOperador());
        });

        pause();
    }
}
