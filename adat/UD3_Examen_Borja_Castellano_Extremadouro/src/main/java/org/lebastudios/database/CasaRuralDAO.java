package org.lebastudios.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CasaRuralDAO
{
    public static CasaRural select(short codCasa, Connection connection) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select codigo, nombre, direccion, localidad, telefono, precio_habitacion, cama_extra, numhabitaciones, " +
                        "alquiler_completa " +
                        "from ALOJAMIENTO a " +
                        "inner join CASARURAL h on a.codigo = h.cod_casa " +
                        "where codigo = ?"))
        {
            preparedStatement.setShort(1, codCasa);

            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (!resultSet.next()) return null;

                return new CasaRural(
                        resultSet.getShort(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getFloat(6),
                        resultSet.getFloat(7),
                        resultSet.getByte(8),
                        resultSet.getString(9).toCharArray()[0]
                );
            }
        }
    }
}
