package org.lebastudios.restapiclientexamen;

import org.lebastudios.restapiclientexamen.entities.Operador;
import org.lebastudios.restapiclientexamen.entities.Telefono;
import org.lebastudios.restapiclientexamen.entities.Traspaso;
import org.lebastudios.restapiclientexamen.http.HttpMethods;

import java.util.Arrays;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
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
        String numeroDelTelefono = preguntarNumeroTelefonoValido();

        if (numeroDelTelefono == null) return;
        
        Traspaso[] traspasos = HttpMethods.get("traspasos?telefono=" + numeroDelTelefono, Traspaso[].class);

        if (traspasos == null)
        {
            System.err.println("Error en la petición al servidor");
            pause();
            return;
        }
        
        if (traspasos.length == 0)
        {
            System.out.println("Este telefono aun no ha sido traspasado entre operadoras o no existe");
        }
        else
        {
            System.out.println("Telefonos");
            Arrays.stream(traspasos).forEach(traspaso ->
            {
                System.out.printf("Numero: %-9s   Operadora Vieja: %d\tOperadora Nueva: %d\tMotivo: %s\n", traspaso.getTelefono(),
                        traspaso.getViejaOperadora(), traspaso.getNuevaOperadora(), traspaso.getMotivo()
                );
            });
        }
        
        pause();
    }

    private static void cambiarDeOperador() 
    {
        String numeroDelTelefono = preguntarNumeroTelefonoValido();

        if (numeroDelTelefono == null) return;
        
        Telefono[] telefonos = HttpMethods.get("telefonos?telefono=" + numeroDelTelefono, Telefono[].class);
        
        if (telefonos == null) 
        {
            System.err.println("Error al intentar encontrar el telefono en l base de datos");
            pause();
            return;
        }
        
        if (telefonos.length == 0) 
        {
            System.err.println("El numero de telefonos no se encuentra en la base de datos");
            pause();
            return;
        }

        System.out.println("Ahora va a tener que indicar la nueva operadora de este telefono");
        pause();
        
        int codNuevaOperadora = obtenerNumeroDeOperadoraPorTeclado();
        
        if (codNuevaOperadora == -1) return;

        System.out.println("Motivo del traspaso: ");
        String motivo = new Scanner(System.in).nextLine();
        
        Traspaso traspaso = new Traspaso(numeroDelTelefono, codNuevaOperadora, motivo);
        
        boolean exito = HttpMethods.put("traspasos", traspaso);

        System.out.println(exito
                ? "El teléfono ha sido traspasado a otra operadora satisfactoriamente"
                : "Error durante el traspaso del telefono a la nueva operadora");

        pause();
    }

    private static void visualizarTelefonoDeUnTitular() 
    {
        System.out.println("Introduce el nombre del titular del que se quieren ver los telefonos");
        String titular = new Scanner(System.in).nextLine();
        
        Telefono[] telefonos = HttpMethods.get("telefonos?titular=" + titular, Telefono[].class);

        if (telefonos == null)
        {
            System.err.println("Error en la petición al servidor");
            pause();
            return;
        }

        if (telefonos.length == 0) 
        {
            System.out.println("Este titular no tiene ningún telefono registrado (No existe en nuestra base de datos " +
                    "tal y como David la definio de cutre)");
        }
        else 
        {
            System.out.println("Telefonos");
            Arrays.stream(telefonos).forEach(tel ->
            {
                System.out.printf("Numero: %s - Operadora: %d\n", tel.getTelefono(), tel.getCodOperador());
            });
        }
        
        pause();
    }

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
        int codOperador = obtenerNumeroDeOperadoraPorTeclado();

        if (codOperador == -1) return;
        
        Telefono[] telefonos = HttpMethods.get("telefonos?codOperador=" + codOperador, Telefono[].class);

        if (telefonos == null)
        {
            System.err.println("Error en la petición al servidor");
            pause();
            return;
        }

        if (telefonos.length == 0) 
        {
            System.out.println("Esta operadora, o bien no existe, o bien no tine ningun numero de telefono asociado");
            pause();
            return;
        }
        
        System.out.println("Telefonos");
        Arrays.stream(telefonos).forEach(tel ->
        {
            System.out.printf("Numero: %s - %s (Operadora: %d)\n", tel.getTelefono(), tel.getTitular(),
                    tel.getCodOperador());
        });

        pause();
    }
    
    private static int obtenerNumeroDeOperadoraPorTeclado()
    {
        Operador[] operadores = HttpMethods.get("operadores", Operador[].class);

        if (operadores == null)
        {
            System.err.println("Error en la petición al servidor");
            pause();
            return - 1;
        }

        Arrays.stream(operadores).forEach(operador ->
        {
            System.out.printf("%d - %s\n", operador.getCodOperador(), operador.getNombre());
        });

        System.out.println("Seleccione alguno de los operadores anteriores (-1 para cancelar):");
        String num = new Scanner(System.in).nextLine();
        
        if (!num.matches("\\d+")) 
        {
            System.err.println("El numero introducido no es válido.");
            pause();
            return -1;
        }
        
        return Integer.parseInt(num);
    }
    
    private static String preguntarNumeroTelefonoValido()
    {
        if (!mostrarTelefonosEnElServer()) return null;

        System.out.println("Seleccione alguno de los telefonos anteriores (-1 para cancelar):");
        String num = new Scanner(System.in).nextLine();

        if (!num.matches("\\d+"))
        {
            System.err.println("El numero introducido no es válido.");
            pause();
            return null;
        }

        return num;
    }

    private static boolean mostrarTelefonosEnElServer()
    {
        Telefono[] telefonos = HttpMethods.get("telefonos", Telefono[].class);

        if (telefonos == null)
        {
            System.err.println("Error en la petición al servidor");
            pause();
            return false;
        }

        Arrays.stream(telefonos).forEach(tel ->
        {
            System.out.printf("Telefono: %s  Operadora: %d  Titular: %s\n", tel.getTelefono(), tel.getCodOperador(), tel.getTitular());
        });
        return true;
    }
}
