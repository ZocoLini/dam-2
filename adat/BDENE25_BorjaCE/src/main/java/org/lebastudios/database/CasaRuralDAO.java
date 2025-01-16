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

    public static short insert(CasaRural casaRural, Connection connection) throws SQLException
    {
        short codAlojamiento = AlojamientoDAO.insert(casaRural, connection);

        if (codAlojamiento == 0) return codAlojamiento;

        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into CASARURAL " +
                "(cod_casa, alquiler_completa) VALUES (?, ?)"))
        {
            preparedStatement.setShort(1, codAlojamiento);
            preparedStatement.setString(2, String.valueOf(casaRural.getAlquilerCompleta()));

            preparedStatement.executeUpdate();
        }

        return codAlojamiento;
    }

    public static boolean delete(CasaRural casaRural, Connection connection) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from CASARURAL where cod_casa = ?"
        ))
        {
            preparedStatement.setShort(1, casaRural.getCodigo());
            preparedStatement.executeUpdate();
        }

        return AlojamientoDAO.delete(casaRural, connection);
    }

    public static void showInfo(CasaRural casaRural, Connection connection)
    {
        StringBuilder stringBuilder = new StringBuilder();

        String alquilerCompleto = casaRural.getAlquilerCompleta() == 'S'
                ? "SÍ"
                : "NO";

        stringBuilder.append("CASA RURAL: ").append(casaRural.getNombre())
                .append("\t").append("Alquiler completo: ").append(alquilerCompleto).append("\n");

        System.out.println(stringBuilder);
        AlojamientoDAO.showActividades(casaRural.getNombre(), connection);
    }
}
