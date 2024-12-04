package org.lebastudios;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmpregadoDAO
{
    public static void subirSalario(String nomeDepartamento, int cantidad)
    {
        String sql = "update EMPREGADO\n" +
                "set salario = (select Salario) + ?\n" +
                "where Num_departamento_pertenece = (select Num_departamento\n" +
                "                                    from DEPARTAMENTO\n" +
                "                                    where Nome_departamento = ?)";
        
        Database.getInstance().getConnections().forEach(connection -> 
        {
            try (PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setInt(1, cantidad);
                statement.setString(2, nomeDepartamento);
                
                statement.executeUpdate();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
}
