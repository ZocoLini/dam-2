package org.lebastudios;

import java.sql.*;

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
        // cambiarDomicilio("123456789", "Rua", 1, "1", 12345, "Vigo");
        //  b)
        // Proxecto proyecto = datosProyecto(1);
        //  c)  
        // noEntendi(3);
        //  d)
        // numEmpleadosDep("INNOVACIÓN");

        // EJ 6
        //  a)
        visualizarTiposResultSet();
        //  b)
        insertarDatosProyecto(new Proxecto(20, "TRT", "Vigo", 1));
        //  c)
        incrementarSalarioDepartamento(1, 45.6f);
        //  d)
        obtenerInfoProyectosProyectosAsignadosMayorQue(3);
    }

    private static void visualizarTiposResultSet()
    {
        Database.getInstance().getConnections().forEach(connection ->
        {
            try
            {
                DatabaseMetaData metaData = connection.getMetaData();

                System.out.println("Tipos de ResultSet soportados:");
                if (metaData.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY))
                {
                    System.out.println("- TYPE_FORWARD_ONLY");
                }
                if (metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE))
                {
                    System.out.println("- TYPE_SCROLL_INSENSITIVE");
                }
                if (metaData.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE))
                {
                    System.out.println("- TYPE_SCROLL_SENSITIVE");
                }

                System.out.println("\nTipos de concurrencia soportados:");
                if (metaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY))
                {
                    System.out.println("- TYPE_FORWARD_ONLY + CONCUR_READ_ONLY");
                }
                if (metaData.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE))
                {
                    System.out.println("- TYPE_FORWARD_ONLY + CONCUR_UPDATABLE");
                }
                if (metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY))
                {
                    System.out.println("- TYPE_SCROLL_INSENSITIVE + CONCUR_READ_ONLY");
                }
                if (metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE))
                {
                    System.out.println("- TYPE_SCROLL_INSENSITIVE + CONCUR_UPDATABLE");
                }
                if (metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY))
                {
                    System.out.println("- TYPE_SCROLL_SENSITIVE + CONCUR_READ_ONLY");
                }
                if (metaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE))
                {
                    System.out.println("- TYPE_SCROLL_SENSITIVE + CONCUR_UPDATABLE");
                }
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }

    private static void insertarDatosProyecto(Proxecto proxecto)
    {
        String sql = "SELECT * FROM PROXECTO";

        Database.getInstance().getConnections().forEach(connection ->
        {
            try (PreparedStatement sentencia = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE))
            {
                ResultSet result = sentencia.executeQuery();
                result.beforeFirst();
                result.moveToInsertRow();
                result.updateInt(1, proxecto.getNumProxecto());
                result.updateString(2, proxecto.getNombreProxecto());
                result.updateString(3, proxecto.getLugar());
                result.updateInt(4, proxecto.getNumDepartamentoController());
                result.insertRow();
                result.moveToCurrentRow();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }

    private static void incrementarSalarioDepartamento(int numDep, float cant)
    {
        String sql = "select * from EMPREGADO where Num_departamento_pertenece = ?";

        Database.getInstance().getConnections().forEach(conn ->
        {
            try (PreparedStatement statement = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE))
            {
                statement.setInt(1, numDep);
                ResultSet rs = statement.executeQuery();

                while (rs.next())
                {
                    rs.updateFloat("Salario", rs.getFloat("Salario") + cant);
                    rs.updateRow();
                }
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }

    private static void obtenerInfoProyectosProyectosAsignadosMayorQue(int numMin)
    {
        String sql =
                "select NSS, Nome + ' ' + Apelido_1 + ' ' + Apelido_2 as NomeCompleto, Localidade, Salario " +
                        "from EMPREGADO " +
                        "where NSS in (select NSS_empregado " +
                                "from EMPREGADO_PROXECTO " +
                                "group by NSS_empregado " +
                                "having COUNT(*) > ?)";

        Database.getInstance().getConnections().forEach(connection ->
        {
            try (PreparedStatement statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE))
            {
                statement.setInt(1, numMin);

                ResultSet rs = statement.executeQuery();
                rs.first();
                System.out.println(
                        rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getFloat(4));
                rs.last();
                System.out.println(
                        rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getFloat(4));
                rs.relative(-2);
                System.out.println(
                        rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getFloat(4));
                rs.afterLast();

                do
                {
                    System.out.println(
                            rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getFloat(4));
                } while (rs.previous());

            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }

    private static void visualizarEmpleadosSegunLocalidad(String nombreLocalidad)
    {
        String sql =
                "select e1.Nome,\n" + "       e1.Apelido_1,\n" + "       e1.Apelido_2,\n" + "       e1.Localidade,\n" +
                        "       e1.Salario,\n" + "       e1.Data_nacemento,\n" + "       e2.Nome,\n" +
                        "       DEPARTAMENTO.Nome_departamento\n" + "from EMPREGADO e1\n" +
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
                    System.out.printf("%s %s %s %s %f %s %s \n", resultSet.getString(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4), resultSet.getFloat(5),
                            resultSet.getString(6), resultSet.getString(7));
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
        String call = "{call pr_cambioDomicilio(?, ?, ?, ?, ?, ?)}";

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

                proyecto[0] = new Proxecto(numeroProyecto, statement.getString(2), statement.getString(3),
                        statement.getInt(4));
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