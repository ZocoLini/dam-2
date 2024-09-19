package EJ6_A2UD1;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

public class EJ6_A2UD1
{
    public static void main(String[] args)
    {
        System.out.println("Introduce la ruta a un directorio");
        String ruta = new Scanner(System.in).nextLine();

        System.out.println("Introduce una subcadena que deben contener en el nombre los ficheros a listar");
        String subcadena = new Scanner(System.in).nextLine();

        File directorio = new File(ruta);

        if (!directorio.exists())
        {
            System.out.println("El directorio no existe");
            return;
        }

        if (!directorio.isDirectory())
        {
            System.out.println("La ruta no corresponde a un directorio");
            return;
        }

        System.out.println("--- LISTANDO EL DIRECTORIO " + directorio.getAbsolutePath() + " ---");

        printDirectory(directorio, 0, subcadena);
    }

    public static void printFile(File file, int indent)
    {
        System.out.println("-".repeat(indent * 4 + 1) + "| " + file.getName() + " <FICHERO> "
                + file.length() / 1024.0 + " Kbytes " +
                LocalDateTime.ofEpochSecond(
                        file.lastModified() / 1000,
                        0,
                        ZoneOffset.UTC).toString().replace('T', ' ')
        );
    }

    public static void printDirectory(File directory, int indent, String subcadena)
    {
        System.out.println("-".repeat(indent * 4 + 1) + "| " + directory.getName() + " <DIR>");
        if (directory.listFiles() == null) return;
        for (File variable : directory.listFiles(file -> file.isDirectory() || file.getName().contains(subcadena)))
        {
            if (variable.isFile())
            {
                printFile(variable, indent + 1);
            }
            else
            {
                printDirectory(variable, indent + 1, subcadena);
            }
        }
    }
}
