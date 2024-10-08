/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a4ud1_alumno;

import CLASESDATOS.Alumno;
import CLASESDATOS.NotaAlumno;

import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class A4UD1_Alumnos {

    public static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

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
                case 1 -> listarDatosAlumnos();
                case 2 -> listarNotasAlumnos();
                case 3 -> addNuevoAlumno();
                case 4 -> visualizarAlumno();
                case 5 -> modificarNumerosAlumno();
                case 6 -> exportarDatosTXT();
                default -> exit = true;
            }
            
            systemPause();
        }
    }
    
    private static int showMenu()
    {
        System.out.println("----  Menu  ----");
        System.out.println("1. Listar datos de los alumnos");
        System.out.println("2. Listar notas de los alumnos");
        System.out.println("3. Añadir un nuevo alumno");
        System.out.println("4. Visualizar un alumno");
        System.out.println("5. Añadir/Eliminar los números de un alumno");
        System.out.println("6. Exportar datos a TXT");
    
        try
        {
            return new Scanner(System.in).nextInt();
        }
        catch (Exception ignore)
        {
            return -1;
        }
    }
    
    private static void listarDatosAlumnos()
    {
        for (int i = 0; i < Alumno.getNumeroRegistros(); i++) 
        {
            System.out.println(Alumno.getAlumno(i + 1));
        }
    }
    
    private static void listarNotasAlumnos()
    {
        for (int i = 0; i < Alumno.getNumeroRegistros(); i++)
        {
            System.out.println(Alumno.getAlumno(i + 1));
            final var notas = NotaAlumno.getNotaAlumno(i + 1);
            
            if (notas == null) continue;
            
            notas.getNotas().forEach(v -> System.out.println("--->" + v));
        }
    }
    
    private static void addNuevoAlumno()
    {
        System.out.println("Vamos a añadir un nuevo alumno: ");
        Alumno alumno;
                
        try
        {
            alumno = Alumno.generate();
            alumno.guardar();
        }
        catch (ParseException e)
        {
            System.out.println("Error al guardar el alumno. El formato de la fecha no es válido.");
            return;
        }

        System.out.println("Vamos a guardar ahora las notas del alumno en cuestión: ");
        NotaAlumno notaAlumno = NotaAlumno.generate(alumno);
        notaAlumno.guardar();
    }
    
    private static void visualizarAlumno()
    {
        System.out.println("Introduzca el número del alumno a visualizar: ");
        int numero = new Scanner(System.in).nextInt();
        
        Alumno alumno = Alumno.getAlumno(numero);
        
        if (alumno == null)
        {
            System.out.println("No se ha encontrado el alumno.");
            return;
        }
        
        System.out.println(alumno);
    }
    
    private static void modificarNumerosAlumno()
    {
        System.out.println("Introduce el numero del alumno del que quieres modificar los numeros: ");
        int numero = new Scanner(System.in).nextInt();
        
        Alumno alumno = Alumno.getAlumno(numero);
        
        if (alumno == null)
        {
            System.out.println("No se ha encontrado el alumno.");
            return;
        }

        System.out.println("Introduce un número de teléfono para añadir o eliminar (* para salir): ");
        
        String telefono = new Scanner(System.in).nextLine();
        
        if (telefono.equals("*")) return;

        System.out.println("¿Quieres añadir o eliminar el número de teléfono? (a/e): ");
        
        String opcion = new Scanner(System.in).nextLine();
        
        if (opcion.equals("a"))
        {
            if (alumno.getTelefonos().contains(telefono))
            {
                System.out.println("El número de teléfono ya existe.");
                return;
            }
            
            alumno.getTelefonos().add(telefono);
        }
        else if (opcion.equals("e"))
        {
            if (!alumno.getTelefonos().contains(telefono)) 
            {
                System.out.println("El número de teléfono no existe.");
                return;
            }
            
            alumno.getTelefonos().remove(telefono);
        }
        else
        {
            System.out.println("Opción no válida.");
        }
        
        alumno.guardar();
    }
    
    private static void exportarDatosTXT()
    {
        try (FileWriter writer = new FileWriter("output.txt"))
        {
            writer.write("-------------------------DATOS ALUMNOS-----------------------\n");
            Alumno alumno;
            for (int i = 1; i <= Alumno.getNumeroRegistros(); i++) 
            {
                alumno = Alumno.getAlumno(i);
                
                if (alumno == null || alumno.isBorrado()) continue;
                
                writer.write(alumno.preattyPrinting()+ "\n");
                writer.write("---------------------------------------------------------\n");
            }
            
            writer.write("TOTAL DE ALUMNOS\t\t\t\t" + Alumno.getNumeroRegistros());
        }
        catch (Exception exception)
        {
        
        }
    }
    
    private static void systemPause()
    {
        System.out.println("Presiona cualquier tecla para continuar...");
        new Scanner(System.in).nextLine();
    }
}
