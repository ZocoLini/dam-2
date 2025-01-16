package org.lebastudios.database;

import java.sql.*;

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
    
    public static String obtenerNombreSede(String nombreHotel, Connection connection) throws SQLException
    {
        try (CallableStatement callableStatement = connection.prepareCall("{call pr_getHotelSede(?, ?)}"))
        {
            callableStatement.setString(1, nombreHotel);
            callableStatement.registerOutParameter(2, Types.VARCHAR);
            
            callableStatement.execute();
            
            return callableStatement.getString(2);
        }
    }

    public static short insert(Hotel hotel, Connection connection) throws SQLException
    {
        short codAlojamiento = AlojamientoDAO.insert(hotel, connection);

        if (codAlojamiento == 0) return codAlojamiento;

        Hotel hotelSede = HotelDAO.select(hotel.getHotelSede(), connection);

        if (hotelSede == null)
        {
            System.err.printf("El hotel '%s' que se ha intentado insertar tiene como se al hotel numero '%d' pero " +
                    "este no existe en la base de datos", hotel.getNombre(), hotel.getHotelSede()
            );
            return 0;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into HOTEL " +
                "(cod_hotel, estrellas, hotelsede) values (?, ?, ?)"))
        {
            preparedStatement.setShort(1, codAlojamiento);
            preparedStatement.setByte(2, hotel.getEstrellas());
            preparedStatement.setShort(3, hotel.getHotelSede());

            preparedStatement.executeUpdate();
        }

        return codAlojamiento;
    }

    public static boolean delete(Hotel hotel, Connection connection) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "update HOTEL set hotelsede = null where hotelsede = ?"
        ))
        {
            preparedStatement.setShort(1, hotel.getCodigo());
            preparedStatement.executeUpdate();
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from HOTEL where cod_hotel = ?"
        ))
        {
            preparedStatement.setShort(1, hotel.getCodigo());
            preparedStatement.executeUpdate();
        }

        return AlojamientoDAO.delete(hotel, connection);
    }

    public static void showInfo(Hotel hotel, Connection connection)
    {
        StringBuilder stringBuilder = new StringBuilder();

        try
        {
            String nombreSede = HotelDAO.obtenerNombreSede(hotel.getNombre(), connection);

            String tipoHotel = hotel instanceof HotelSpa ? "HOTEL SPA" : "HOTEL";

            stringBuilder.append(tipoHotel).append(": ").append(hotel.getNombre())
                    .append("\t").append("SEDE: ").append(nombreSede).append("\n");

            System.out.println(stringBuilder);
            AlojamientoDAO.showActividades(hotel.getNombre(), connection);
        }
        catch (SQLException e)
        {
            System.err.println("Error al intentar mostrar la información del hotel " + hotel.getNombre());
        }
    }
}
