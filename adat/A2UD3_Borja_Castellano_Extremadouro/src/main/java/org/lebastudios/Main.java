package org.lebastudios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main
{
    public static void main(String[] args)
    {
        // EJ 1 
        //  a)
        // EmpregadoDAO.subirSalario("PERSOAL", 26000);
        //  b)
        // DepartamentoDAO.insertar(56, "MARKETING", "0010010");
        //  c)
        // EmpregadoProxectoDAO.borrarEmpregadoDeProyecto("0010010", 8);
        
        // EJ 2
        // visualizarEmpleadosSegunLocalidad("Vigo");
        
        // EJ 3
        //  a)
        // ProxectoDAO.cambiarDepartamentoControla("INNOVACIÓN", "PORTAL");
        //  b)
        // ProxectoDAO.insert(new Proxecto(11, "TRT", "Vigo", 4));
        //  c)
        // ProxectoDAO.remove(11);
        
        // EJ 4
        // ProxectoDAO.selectAll("INNOVACIÓN");
        
        // EJ 5
        
        
    }
    
    private static void visualizarEmpleadosSegunLocalidad(String nombreLocalidad)
    {
        String sql = "select e1.Nome,\n" +
                "       e1.Apelido_1,\n" +
                "       e1.Apelido_2,\n" +
                "       e1.Localidade,\n" +
                "       e1.Salario,\n" +
                "       e1.Data_nacemento,\n" +
                "       e2.Nome,\n" +
                "       DEPARTAMENTO.Nome_departamento\n" +
                "from EMPREGADO e1\n" +
                "         inner join EMPREGADO e2 on e1.NSS = e2.NSS\n" +
                "         inner join DEPARTAMENTO on e1.Num_departamento_pertenece = DEPARTAMENTO.Num_departamento\n" +
                "where e1.Localidade = ?;";

        Database.getInstance().getConnections().forEach(connection ->
        {
            try (PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setString(1, nombreLocalidad);
                
                ResultSet resultSet = statement.executeQuery();
                
                while (resultSet.next()) 
                {
                    System.out.printf("%s %s %s %s %f %s %s \n",
                            resultSet.getString(1), 
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getFloat(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    );
                }
                
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
}