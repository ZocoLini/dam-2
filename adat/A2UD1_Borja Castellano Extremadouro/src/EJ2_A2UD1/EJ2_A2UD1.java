package EJ2_A2UD1;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

public class EJ2_A2UD1
{
    public static void main(String[] args)
    {
        System.out.println("Introduce la ruta a un directorio");
        String ruta = new Scanner(System.in).nextLine();

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

        for (File variable : directorio.listFiles())
        {
            if (variable.isFile())
            {
                printFile(variable);
            }
            else
            {
                printDirectory(variable);
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

    public static void printDirectory(File directory)
    {
        System.out.println("-| " + directory.getName() + " <DIR>");
    }
}
