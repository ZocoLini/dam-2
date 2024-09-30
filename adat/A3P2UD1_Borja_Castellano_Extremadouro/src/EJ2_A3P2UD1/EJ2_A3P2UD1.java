package EJ2_A3P2UD1;

import java.io.*;

public class EJ2_A3P2UD1
{
    private static final File DATA_FILE = new File("data.txt");
    private static final File LOG_FILE = new File("ficherolog.txt");
    private static final File ALUMS_FILE = new File("A3P2UD1_FicherosSecTexto/ALUMNOS");


    public static void main(String[] args)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE));
             BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE)))
        {
            String line;
            Alumno alumno;

            while ((line = reader.readLine()) != null)
            {
                alumno = new Alumno(line);

                if (alumno.generateDir(ALUMS_FILE))
                {
                    writer.write(alumno.getNombre() + "  -----> se creo correctamente el directorio\n");
                }
            }
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
