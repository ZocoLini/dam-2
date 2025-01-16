package org.lebastudios.ej3;

import com.google.gson.Gson;
import org.lebastudios.database.Database;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class Main
{
    public static void main(String[] args)
    {
        try (FileReader json = new FileReader("AlojamientosPrecios.json"))
        {
          AlojaminetosPrecios alojaminetosPrecios = new Gson().fromJson(json, AlojaminetosPrecios.class);
          alojaminetosPrecios.realizarActualizaciones(Database.getInstance().getConnection());
        }
        catch (IOException e)
        {
            System.err.println("Error de E/S al leer el archivo JSON");
        }
        catch (SQLException e)
        {
            System.err.println("Ha ocurrido un error al intentar realizar las actualizaciones a partir del archivo json");
        }
    }
}
