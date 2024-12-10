package org.lebastudios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ProxectoDAO
{
    public static void cambiarDepartamentoControla(String nombreDepartamento, String nombreProyecto)
    {
        String sql = """
                update PROXECTO
                set Num_departamento_controla = (select Num_departamento from DEPARTAMENTO where Nome_departamento = ?)
                where Nome_proxecto = ?""";

        Database.getInstance().getConnections().forEach(connection ->
        {
            try (PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setString(1, nombreDepartamento);
                statement.setString(2, nombreProyecto);

                statement.executeUpdate();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
    
    public static Collection<Proxecto> selectAll(String nombreDepartamento)
    {
        Collection<Proxecto> proxectos = new ArrayList<>();

        String sql = """
                select num_proxecto, nome_proxecto, lugar, num_departamento_controla
                from PROXECTO
                where Num_departamento_controla = (select Num_departamento from DEPARTAMENTO where Nome_departamento = ?)""";

        Database.getInstance().getConnections().forEach(connection ->
        {
            try (PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setString(1, nombreDepartamento);

                try (ResultSet rs = statement.executeQuery())
                {
                    while (rs.next())
                    {
                        Proxecto proxecto = new Proxecto();
                        proxecto.setNumProxecto(rs.getInt(1));
                        proxecto.setNombreProxecto(rs.getString(2));
                        proxecto.setLugar(rs.getString(3));
                        proxecto.setNumDepartamentoController(rs.getInt(4));
                    }
                }
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
        
        return proxectos;
    }
    
    public static void insert(Proxecto proxecto)
    {
        String sql = """
                insert into PROXECTO (Num_proxecto, Nome_proxecto, Lugar, Num_departamento_controla)\s
                values (?, ?, ?, ?)""";

        Database.getInstance().getConnections().forEach(connection ->
        {
            try (PreparedStatement statement = connection.prepareStatement(sql))
            {
                statement.setInt(1, proxecto.getNumProxecto());
                statement.setString(2, proxecto.getNombreProxecto());
                statement.setString(3, proxecto.getLugar());
                statement.setInt(4, proxecto.getNumDepartamentoController());

                statement.executeUpdate();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
    
    public static void remove(int numProxecto)
    {
        String sql = """
                delete from EMPREGADO_PROXECTO
                where Num_proxecto = ?;""";
        String sql2 = """
                delete from PROXECTO
                where Num_proxecto = ?;
                """;

        Database.getInstance().getConnections().forEach(connection ->
        {
            try (PreparedStatement statement = connection.prepareStatement(sql);
                PreparedStatement statement2 = connection.prepareStatement(sql2))
            {
                statement.setInt(1, numProxecto);
                statement2.setInt(1, numProxecto);
                
                statement.executeUpdate();
                statement2.executeUpdate();
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        });
    }
}
