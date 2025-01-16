package org.lebastudios.database;

import org.lebastudios.ej2.ConfirmationRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    static short insert(Alojamiento alojamiento, Connection connection) throws SQLException
    {
        short result = getAlojaminetoId(alojamiento.getNombre(), connection);

        if (result != 0)
        {
            System.err.printf(
                    "Se ha intentado insertar un alojamiento con nombre '%s' el cual ya está en uso en la base de " +
                            "datos\n",
                    alojamiento.getNombre()
            );
            return 0;
        }
        ;

        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into ALOJAMIENTO " +
                "(nombre, direccion, localidad, telefono, precio_habitacion, cama_extra, numhabitaciones) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS))
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

        Hotel hotelSede = HotelDAO.select(hotel.getHotelSede(), connection);

        if (hotelSede == null)
        {
            System.err.printf("El hotel '%s' que se ha intentado insertar tiene como se al hotel numero '%d' pero " +
                    "este no existe en la base de datps", hotel.getNombre(), hotel.getHotelSede()
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

    public static boolean delete(String nombreAlojamiento, Connection connection) throws SQLException
    {
        short idAlojamiento = getAlojaminetoId(nombreAlojamiento, connection);

        if (idAlojamiento == 0)
        {
            System.err.println("Se ha intentado eliminar un alojamiento que no existe en la base de datos");
            return false;
        }

        Alojamiento alojamiento = AlojamientoDAO.select(idAlojamiento, connection);

        if (alojamiento == null)
        {
            System.err.println("No se ha podido recuperar satisfactoriamente el alojamiento desde la base de datos");
            return false;
        }

        CasaRural casaRural = CasaRuralDAO.select(idAlojamiento, connection);

        if (casaRural != null)
        {
            showInfo(casaRural, connection);
            if (ConfirmationRequest.askConfirmation("Esta seguro de que desea borrar la Casa Rural?")) 
            {
                return delete(casaRural, connection);
            }
            else 
            {
                return false;
            }
        }

        HotelSpa hotelSpa = HotelSpaDAO.select(idAlojamiento, connection);

        if (hotelSpa != null)
        {
            showInfo(hotelSpa, connection);
            if (ConfirmationRequest.askConfirmation("Esta seguro de que desea borrar el Hotel SPA?"))
            {
                return delete(hotelSpa, connection);
            }
            else
            {
                return false;
            }
        }

        Hotel hotel = HotelDAO.select(idAlojamiento, connection);

        showInfo(hotel, connection);
        if (ConfirmationRequest.askConfirmation("Esta seguro de que desea borrar el Hotel?"))
        {
            return delete(hotel, connection);
        }
        else
        {
            return false;
        }
    }

    private static boolean delete(Alojamiento alojamiento, Connection connection) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from ALOJAMIENTO_ACTIVIDAD where cod_alojamiento = ?"
        ))
        {
            preparedStatement.setShort(1, alojamiento.getCodigo());
            preparedStatement.executeUpdate();
        }
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from ALOJAMIENTO where codigo = ?"
        ))
        {
            preparedStatement.setShort(1, alojamiento.getCodigo());
            preparedStatement.executeUpdate();
        }
        
        return true;
    }

    private static boolean delete(CasaRural casaRural, Connection connection) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from CASARURAL where cod_casa = ?"
        ))
        {
            preparedStatement.setShort(1, casaRural.getCodigo());
            preparedStatement.executeUpdate();
        }
        
        return delete((Alojamiento) casaRural, connection);
    }

    private static boolean delete(Hotel hotel, Connection connection) throws SQLException
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
        
        return delete((Alojamiento) hotel, connection);
    }

    private static boolean delete(HotelSpa hotelSpa, Connection connection) throws SQLException
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from HOTELSPA_SERVICIOS where cod_spa = ?"
        ))
        {
            preparedStatement.setShort(1, hotelSpa.getCodigo());
            preparedStatement.executeUpdate();
        }
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "delete from HOTELSPA where cod_spa = ?"
        ))
        {
            preparedStatement.setShort(1, hotelSpa.getCodigo());
            preparedStatement.executeUpdate();
        }
        
        return delete((Hotel) hotelSpa, connection);
    }

    private static void showInfo(Hotel hotel, Connection connection)
    {
        StringBuilder stringBuilder = new StringBuilder();

        try
        {
            String nombreSede = HotelDAO.obtenerNombreSede(hotel.getNombre(), connection);

            String tipoHotel = hotel instanceof HotelSpa ? "HOTEL SPA" : "HOTEL";
            
            stringBuilder.append(tipoHotel).append(": ").append(hotel.getNombre())
                    .append("\t").append("SEDE: ").append(nombreSede).append("\n");

            System.out.println(stringBuilder);
            showActividades(hotel.getNombre(), connection);
        }
        catch (SQLException e)
        {
            System.err.println("Error al intentar mostrar la información del hotel " + hotel.getNombre());
        }
    }

    private static void showInfo(HotelSpa hotelSpa, Connection connection)
    {
        showInfo((Hotel) hotelSpa, connection);
    }

    private static void showInfo(CasaRural casaRural, Connection connection)
    {
        StringBuilder stringBuilder = new StringBuilder();

        String alquilerCompleto = casaRural.getAlquilerCompleta() == 'S' 
                ? "SÍ"
                : "NO";

        stringBuilder.append("CASA RURAL: ").append(casaRural.getNombre())
                .append("\t").append("Alquiler completo: ").append(alquilerCompleto).append("\n");

        System.out.println(stringBuilder);
        showActividades(casaRural.getNombre(), connection);
    }

    private static void showActividades(String nombreAlojamiento, Connection connection)
    {
        try
        {
            List<String> actividades = getNombreActividades(nombreAlojamiento, connection);

            StringBuilder stringBuilder = new StringBuilder("ACTIVIDADES\n-------------------------\n");

            actividades.forEach(nombre -> stringBuilder.append(nombre).append("\n"));

            stringBuilder.append("-------------------------\n")
                    .append(actividades.size()).append(" actividades").append("\n");

            System.out.println(stringBuilder);

        }
        catch (SQLException e)
        {
            System.err.println(
                    "Error al mostrar el nombre de las actividades para el alojamiento " + nombreAlojamiento
            );
        }
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

    private static List<String> getNombreActividades(String nombreAlojamiento, Connection connection)
            throws SQLException
    {
        List<String> nombresActividades = new ArrayList<>();

        try (CallableStatement callableStatement = connection.prepareCall("{call pr_showActividadesInfo(?)}"))
        {
            callableStatement.setString(1, nombreAlojamiento);

            callableStatement.execute();

            try (ResultSet resultSet = callableStatement.getResultSet())
            {
                while (resultSet.next())
                {
                    nombresActividades.add(resultSet.getString(1));
                }
            }
        }

        return nombresActividades;
    }
}
