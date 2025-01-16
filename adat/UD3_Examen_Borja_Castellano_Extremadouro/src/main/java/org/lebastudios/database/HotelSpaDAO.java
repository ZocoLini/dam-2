package org.lebastudios.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelSpaDAO
{
    public static HotelSpa select(short codHotel, Connection connection) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select codigo, nombre, direccion, localidad, telefono, precio_habitacion, cama_extra, numhabitaciones, " +
                        "estrellas, hotelsede, gorro, capacidad " +
                        "from ALOJAMIENTO a " +
                        "inner join HOTEL h on a.codigo = h.cod_hotel " +
                        "inner join HOTELSPA H2 on h.cod_hotel = H2.cod_spa " +
                        "where codigo = ?"))
        {
            preparedStatement.setShort(1, codHotel);

            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (!resultSet.next()) return null;

                return new HotelSpa(
                        resultSet.getShort(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getFloat(6),
                        resultSet.getFloat(7),
                        resultSet.getByte(8),
                        resultSet.getByte(9),
                        resultSet.getShort(10),
                        resultSet.getString(11).toCharArray()[0],
                        resultSet.getByte(12)
                );
            }
        }
    }
}
