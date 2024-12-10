package org.lebastudios;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmpregadoProxectoDAO
{
    public static void borrarEmpregadoDeProyecto(String nssEmpleado, int numProxeco)
    {
        String sql = "delete from EMPREGADO_PROXECTO where NSS_empregado = ? and Num_proxecto = ?";

        Database.getInstance().getConnections().forEach(connection ->
        {
            try (PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setString(1, nssEmpleado);
                statement.setInt(2, numProxeco);
                
                statement.execute();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
}
