package EJ4_A2UD1;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

public class EJ4_A2UD1
{
    public static void main(String[] args)
    {
        System.out.println("Introduce la ruta a un directorio");
        String ruta = new Scanner(System.in).nextLine();

        File directorio = new File(ruta);

        System.out.println("Introduce una extensión de archivo");
        String extension = new Scanner(System.in).nextLine();

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

        for (File variable : directorio.listFiles(file -> file.getName().endsWith("." + extension)))
        {
            if (variable.isFile())
            {
                printFile(variable);
            }
        }
    }

    public static void printFile(File file)
    {
        System.out.println("-| " + file.getName() + " <FICHERO> "
                + file.length() / 1024.0 + " Kbytes " +
                LocalDateTime.ofEpochSecond(
                        file.lastModified() / 1000,
                        0,
                        ZoneOffset.UTC).toString().replace('T', ' ')
        );
    }
}
