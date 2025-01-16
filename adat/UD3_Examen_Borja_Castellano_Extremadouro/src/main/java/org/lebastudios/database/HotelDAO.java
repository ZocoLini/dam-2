package org.lebastudios.database;

import org.lebastudios.sqlx.SQLx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelDAO 
{
    public static Hotel select(short codHotel, Connection connection) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select codigo, nombre, direccion, localidad, telefono, precio_habitacion, cama_extra, numhabitaciones, " +
                "estrellas, hotelsede " +
                "from ALOJAMIENTO a " +
                "inner join HOTEL h on a.codigo = h.cod_hotel " +
                        "where codigo = ?"))
        {
            preparedStatement.setShort(1, codHotel);
            
            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (!resultSet.next()) return null;
                
                return new Hotel(
                        resultSet.getShort(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getFloat(6),
                        resultSet.getFloat(7),
                        resultSet.getByte(8),
                        resultSet.getByte(9),
                        resultSet.getShort(10)
                );
            }
        }
    }
}
