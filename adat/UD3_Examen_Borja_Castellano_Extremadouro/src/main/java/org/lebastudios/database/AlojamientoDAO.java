package org.lebastudios.database;

import org.lebastudios.sqlx.SQLx;

import java.sql.*;

public class AlojamientoDAO
{
    public static short getAlojaminetoId(String nombreAlojamiento, Connection connection) throws SQLException
    {
        try (CallableStatement callableStatement = connection.prepareCall("{? = CALL fn_getAlojaminetoPorNombre(?)}"))
        {
            callableStatement.registerOutParameter(1, Types.SMALLINT);
            callableStatement.setString(2, nombreAlojamiento);

            callableStatement.execute();

            return callableStatement.getShort(1);
        }
    }

    private static short insert(Alojamiento alojamiento, Connection connection) throws SQLException
    {
        short result = getAlojaminetoId(alojamiento.getNombre(), connection);

        if (result != 0) return 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into ALOJAMIENTO " +
                "(nombre, direccion, localidad, telefono, precio_habitacion, cama_extra, numhabitaciones) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"))
        {
            preparedStatement.setString(1, alojamiento.getNombre());
            preparedStatement.setString(2, alojamiento.getDireccion());
            preparedStatement.setString(3, alojamiento.getLocalidad());
            preparedStatement.setString(4, alojamiento.getTelefono());
            preparedStatement.setDouble(5, alojamiento.getPrecioHabitacion());
            preparedStatement.setDouble(6, alojamiento.getCamaExtra());
            preparedStatement.setByte(7, alojamiento.getNumHabitaciones());

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys())
            {
                if (resultSet.next())
                {
                    return resultSet.getShort(1);
                }
                else
                {
                    return 0;
                }
            }
        }
    }

    public static short insert(CasaRural casaRural, Connection connection) throws SQLException
    {
        short codAlojamiento = insert((Alojamiento) casaRural, connection);

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

    public static short insert(Hotel hotel, Connection connection) throws SQLException
    {
        short codAlojamiento = insert((Alojamiento) hotel, connection);

        if (codAlojamiento == 0) return codAlojamiento;

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

    public static short insert(HotelSpa hotelSpa, Connection connection) throws SQLException
    {
        short codAlojamiento = insert((Hotel) hotelSpa, connection);

        if (codAlojamiento == 0) return codAlojamiento;

        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into HOTELSPA " +
                "(cod_spa, gorro, capacidad) values (?, ?, ?)"))
        {
            preparedStatement.setShort(1, codAlojamiento);
            preparedStatement.setString(2, String.valueOf(hotelSpa.getGorro()));
            preparedStatement.setByte(3, hotelSpa.getCapacidad());

            preparedStatement.executeUpdate();
        }

        return codAlojamiento;
    }

    public static Short existsHotel(String nombreAlojamiento, Connection connection) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement("select cod_hotel " +
                "from HOTEL where cod_hotel in (select codigo from ALOJAMIENTO a where a.nombre = ?)"))
        {
            preparedStatement.setString(1, nombreAlojamiento);

            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getShort(1);
                }
                else
                {
                    return null;
                }
            }
        }
    }

    public static void delete(String nombreAlojamiento, Connection connection) throws SQLException
    {
        short idAlojamiento = getAlojaminetoId(nombreAlojamiento, connection);

        if (idAlojamiento == 0)
        {
            System.err.println("Se ha intentado eliminar un alojamineto que no existe en la base de datos");
            return;
        }

        Alojamiento alojamiento = SQLx.query("select * from ALOJAMIENTO", connection, Alojamiento.class);

        if (alojamiento == null)
        {
            System.err.println("No se ha podido recuperar satisfactoriamente el alojamiento desde la base de datos");
            return;
        }

        CasaRural casaRural = SQLx.query("select * from CASARURAL where cod_casa = " + idAlojamiento, connection,
                CasaRural.class);

        if (casaRural != null)
        {
            showInfo(casaRural, connection);
            delete(casaRural, connection);
        }
        else
        {
            HotelSpa hotelSpa = SQLx.query("select * from HOTELSPA where cod_spa = " + idAlojamiento, connection,
                    HotelSpa.class);

            if (hotelSpa != null)
            {
                showInfo(hotelSpa, connection);
                delete(hotelSpa, connection);
            }
            else
            {
                Hotel hotel = SQLx.query("select * from HOTEL where cod_hotel = " + idAlojamiento, connection,
                        Hotel.class);
                
                showInfo(hotel, connection);
                delete(hotel, connection);
            }
        }
    }

    private static void delete(Alojamiento alojamiento, Connection connection)
    {
        
    }   
    
    private static void delete(CasaRural casaRural, Connection connection)
    {
        
    }
    
    private static void delete(Hotel hotel, Connection connection)
    {
        
    }
    
    private static void delete(HotelSpa hotelSpa, Connection connection)
    {
        
    }
    
    private static void showInfo(Hotel hotel, Connection connection)
    {
        StringBuilder stringBuilder = new StringBuilder();

        Hotel hotelSede = null;
        try
        {
            hotelSede = HotelDAO.select(hotel.getHotelSede(), connection);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        stringBuilder.append("HOTEL: ").append(hotel.getNombre())
                .append("\t").append("SEDE: ").append(hotelSede.getNombre()).append("\n");
        
        
    }

    private static void showInfo(HotelSpa hotelSpa, Connection connection)
    {

    }

    private static void showInfo(CasaRural casaRural, Connection connection)
    {

    }
    
    public static Alojamiento select(short codigo, Connection connection) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select codigo, nombre, direccion, localidad, telefono, precio_habitacion, cama_extra, numhabitaciones " +
                        "from ALOJAMIENTO where codigo = ?"))
        {
            preparedStatement.setShort(1, codigo);
            
            try (ResultSet resultSet = preparedStatement.executeQuery())
            {
                if (!resultSet.next()) 
                {
                    return null;
                }
                
                return new Alojamiento(
                        resultSet.getByte(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getFloat(6),
                        resultSet.getFloat(7),
                        resultSet.getByte(8)
                );
            }
        }
    }
}
