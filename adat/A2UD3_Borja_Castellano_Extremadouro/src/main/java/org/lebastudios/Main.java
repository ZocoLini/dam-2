package org.lebastudios;

import java.sql.CallableStatement;
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
        //  a)
        cambiarDomicilio("123456789", "Rua", 1, "1", 12345, "Vigo");
        //  b)
        Proxecto proyecto = datosProyecto(1);
        //  c)  
        noEntendi(3);
        //  d)
        numEmpleadosDep("INNOVACIÓN");
        
        // EJ 6

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

    private static void cambiarDomicilio(String nss, String rua, int calle, String piso, int cp, String localidad)
    {
        String call = "{call pr_cambiarDomicilio(?, ?, ?, ?, ?, ?)}";

        Database.getInstance().getConnections().forEach(session ->
        {
            try (CallableStatement statement = session.prepareCall(call))
            {
                statement.setString(1, nss);
                statement.setString(2, rua);
                statement.setInt(3, calle);
                statement.setString(4, piso);
                statement.setInt(5, cp);
                statement.setString(6, localidad);

                statement.execute();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }

    private static Proxecto datosProyecto(int numeroProyecto)
    {
        String call = "{call pr_datosProxecto(?, ?, ?, ?)}";

        Proxecto[] proyecto = new Proxecto[1];

        Database.getInstance().getConnections().forEach(session ->
        {
            try (CallableStatement statement = session.prepareCall(call))
            {
                statement.setInt(1, numeroProyecto);
                statement.registerOutParameter(2, java.sql.Types.VARCHAR);
                statement.registerOutParameter(3, java.sql.Types.VARCHAR);
                statement.registerOutParameter(4, java.sql.Types.INTEGER);

                statement.execute();

                proyecto[0] = new Proxecto(
                        numeroProyecto,
                        statement.getString(2),
                        statement.getString(3),
                        statement.getInt(4)
                );
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });

        return proyecto[0];
    }

    private static void noEntendi(int numMin)
    {
        String call = "{call pr_DepartControlaProxe(?)}";
        
        Database.getInstance().getConnections().forEach(session ->
        {
            try (CallableStatement statement = session.prepareCall(call))
            {
                statement.setInt(1, numMin);

                statement.execute();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }

    private static int numEmpleadosDep(String nombreDepartamento)
    {
        String call = "{?= call fn_nEmpDepart(?)}";
        
        int[] numEmpleados = new int[1];
        
        Database.getInstance().getConnections().forEach(session ->
        {
            try (CallableStatement statement = session.prepareCall(call))
            {
                statement.setString(2, nombreDepartamento);
                statement.registerOutParameter(1, java.sql.Types.INTEGER);

                statement.execute();

                numEmpleados[0] = statement.getInt(1);
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
        
        return numEmpleados[0];
    }
}