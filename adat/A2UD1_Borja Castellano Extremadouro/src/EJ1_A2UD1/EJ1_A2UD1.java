package EJ1_A2UD1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class EJ1_A2UD1
{
    private static final File file = new File("output.txt");
    public static void main(String[] args)
    {
        try
        {
             OutputStream out = showMenu();

            showData(out);
        }
        catch (IOException e)
        {
            System.out.println("Error al crear el fichero");
        }
    }

    private static void showData(OutputStream out) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("--- LISTANDO UNIDADES ---\n");
        
        File cDrive = new File("C:\\");
        File dDrive = new File("D:\\");
        
        sb.append(unidadToString(cDrive));
        sb.append(unidadToString(dDrive));
        
        out.write(sb.toString().getBytes());
    }

    public static String unidadToString(File unidad)
    {
        final long freeSpace = unidad.getFreeSpace();
        final long totalSpace = unidad.getTotalSpace();
        
        return "Unidad " + unidad.getAbsolutePath() + "\n" +
                " ".repeat(4) + "Espacio libre: " + freeSpace + " bytes\n" +
                " ".repeat(4) + "Espacio ocupado: " + (totalSpace - freeSpace) + " bytes\n" +
                " ".repeat(4) + "Espacio total: " + totalSpace + " bytes\n";
    }
    
    public static OutputStream showMenu() throws IOException
    {
        if (!file.exists()) file.createNewFile();
        
        System.out.println("Menu de opciones");
        System.out.println("--------------------");
        System.out.println("[P] listado por pantalla");
        System.out.println("[D] listado en fichero");
        System.out.println("--------------------");
        System.out.println("Elija opción ------------------->:");
        
        return switch (new Scanner(System.in).nextLine()) 
        {
            case "P" -> System.out;
            case "D" -> new FileOutputStream(file);
            default -> showMenu();
        };
    }
}
