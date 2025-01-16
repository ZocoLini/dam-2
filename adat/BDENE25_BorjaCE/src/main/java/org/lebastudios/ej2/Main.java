package org.lebastudios.ej2;

import org.lebastudios.database.AlojamientoDAO;
import org.lebastudios.database.Database;

import java.sql.SQLException;

public class Main
{
    public static void main(String[] args)
    {
        Database.getInstance().connectTransaction(connection ->
        {
            try
            {
                boolean result = AlojamientoDAO.delete("Casa Esmeralda", connection);

                System.out.println(result
                        ? "Se ha completado el borrado del alojamiento"
                        : "No se ha completado el borrado del alojamiento"
                );
            }
            catch (SQLException e)
            {
                System.out.println("Ha ocurrido un error con la base de datos al intentar realizar la eliminación");
                throw new RuntimeException(e);
            }
        });
        Database.getInstance().connectTransaction(connection ->
        {
            try
            {
                boolean result = AlojamientoDAO.delete("La pepita", connection);

                System.out.println(result
                        ? "Se ha completado el borrado del alojamiento"
                        : "No se ha completado el borrado del alojamiento"
                );
            }
            catch (SQLException e)
            {
                System.out.println("Ha ocurrido un error con la base de datos al intentar realizar la eliminación");
                throw new RuntimeException(e);
            }
        });
        Database.getInstance().connectTransaction(connection ->
        {
            try
            {
                boolean result = AlojamientoDAO.delete("Mi casa", connection);

                System.out.println(result
                        ? "Se ha completado el borrado del alojamiento"
                        : "No se ha completado el borrado del alojamiento"
                );
            }
            catch (SQLException e)
            {
                System.out.println("Ha ocurrido un error con la base de datos al intentar realizar la eliminación");
                throw new RuntimeException(e);
            }
        });
        Database.getInstance().connectTransaction(connection ->
        {
            try
            {
                boolean result = AlojamientoDAO.delete("Esto no existe", connection);


                System.out.println(result 
                        ? "Se ha completado el borrado del alojamiento"
                        : "No se ha completado el borrado del alojamiento"
                );
            }
            catch (SQLException e)
            {
                System.out.println("Ha ocurrido un error con la base de datos al intentar realizar la eliminación");
                throw new RuntimeException(e);
            }
        });
        Database.getInstance().connectTransaction(connection ->
        {
            try
            {
                boolean result = AlojamientoDAO.delete("Torres del mar", connection);

                System.out.println(result
                        ? "Se ha completado el borrado del alojamiento"
                        : "No se ha completado el borrado del alojamiento"
                );
            }
            catch (SQLException e)
            {
                System.out.println("Ha ocurrido un error con la base de datos al intentar realizar la eliminación");
                throw new RuntimeException(e);
            }
        });
    }
}
