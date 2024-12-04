package org.lebastudios;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class DepartamentoDAO
{
    public static void insertar(int numero, String nombre, String nssDirector)
    {
        String sql = "insert into DEPARTAMENTO (Num_departamento, Nome_departamento, NSS_dirige, Data_direccion)\n" +
                "VALUES (?, ?, ?, ?)";

        Database.getInstance().getConnections().forEach(connection ->
        {
            try (PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setInt(1, numero);
                statement.setString(2, nombre);
                statement.setString(3, nssDirector);
                statement.setDate(4, Date.valueOf(LocalDate.now()));

                statement.executeUpdate();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
}
