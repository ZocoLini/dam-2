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

    public static boolean delete(String nombreAlojamiento, Connection connection) throws SQLException
    {
        // Usando un pocedimiento creado con anterioridad comprobamos si existe el alojamiento
        
        short idAlojamiento = getAlojaminetoId(nombreAlojamiento, connection);

        if (idAlojamiento == 0)
        {
            System.err.println("Se ha intentado eliminar un alojamiento que no existe en la base de datos");
            return false;
        }

        // Una vez confirmamos su existencia objetemos el objeto Alojamiento desde la base de datos
        
        Alojamiento alojamiento = AlojamientoDAO.select(idAlojamiento, connection);

        if (alojamiento == null)
        {
            System.err.println("No se ha podido recuperar satisfactoriamente el alojamiento desde la base de datos");
            return false;
        }

        // Ahora vamos a intentar ver que tipo de alojamiento es, para ello haremos un select de cada tipo y comprobamos
        // si se nos devolvio un objeto diferente de null. Notese que debemos comprobar primero el HotelSPA antes que 
        // el Hotel ya que la existencia de un HotelSPA implica la existencia de un Hotel pero en el sentido contrario
        // la implicacion no es cierta.
        
        CasaRural casaRural = CasaRuralDAO.select(idAlojamiento, connection);

        if (casaRural != null)
        {
            CasaRuralDAO.showInfo(casaRural, connection);
            if (ConfirmationRequest.askConfirmation("Esta seguro de que desea borrar la Casa Rural?")) 
            {
                return CasaRuralDAO.delete(casaRural, connection);
            }
            else 
            {
                return false;
            }
        }

        HotelSpa hotelSpa = HotelSpaDAO.select(idAlojamiento, connection);

        if (hotelSpa != null)
        {
            HotelSpaDAO.showInfo(hotelSpa, connection);
            if (ConfirmationRequest.askConfirmation("Esta seguro de que desea borrar el Hotel SPA?"))
            {
                return HotelSpaDAO.delete(hotelSpa, connection);
            }
            else
            {
                return false;
            }
        }

        Hotel hotel = HotelDAO.select(idAlojamiento, connection);

        HotelDAO.showInfo(hotel, connection);
        if (ConfirmationRequest.askConfirmation("Esta seguro de que desea borrar el Hotel?"))
        {
            return HotelDAO.delete(hotel, connection);
        }
        else
        {
            return false;
        }
    }

    public static boolean delete(Alojamiento alojamiento, Connection connection) throws SQLException
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

    static void showActividades(String nombreAlojamiento, Connection connection)
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
