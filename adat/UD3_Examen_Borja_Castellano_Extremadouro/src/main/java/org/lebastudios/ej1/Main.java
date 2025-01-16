package org.lebastudios.ej1;

import org.lebastudios.database.*;

import java.sql.SQLException;

public class Main
{
    public static void main(String[] args)
    {
        Database.getInstance().connectTransaction(connection ->
        {
            try
            {
                AlojamientoDAO.insert(new CasaRural("Casa Esmeralda", "Ermida 23", "Pazos de Borben", 
                        "627803345", 25, 25, (byte) 6, 'S'),
                        connection
                );
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });

        Database.getInstance().connectTransaction(connection ->
        {
            try
            {
                AlojamientoDAO.insert(new Hotel("La pepita", "Cabadiña 5", "Arcade",
                                "657429521", 65, 100, (byte) 2, (byte) 5, (short) 10),
                        connection
                );
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });

        Database.getInstance().connectTransaction(connection ->
        {
            try
            {
                AlojamientoDAO.insert(new HotelSpa("Mi casa", "Octubre 13, Nº 5", "Valladolid",
                                "513698427", 55, 95, (byte) 6, (byte) 4, (short) 20, 
                                'S', (byte) 20),
                        connection
                );
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
        
        Database.getInstance().connectTransaction(connection ->
        {
            try
            {
                AlojamientoDAO.insert(new Hotel("La pepita", "Cabadiña 5", "Arcade",
                                "657429521", 65, 100, (byte) 2, (byte) 5, (short) 10),
                        connection
                );
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });

        Database.getInstance().connectTransaction(connection ->
        {
            try
            {
                AlojamientoDAO.insert(new Hotel("El Edén", "Nacional 55", "Sevilla",
                                "845632784", 35, 10, (byte) 2, (byte) 3, (short) 98173264),
                        connection
                );
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
}
